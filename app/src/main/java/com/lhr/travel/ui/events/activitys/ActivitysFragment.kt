package com.lhr.travel.ui.events.activitys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhr.travel.R
import com.lhr.travel.data.activitys.ActivitysResponse
import com.lhr.travel.databinding.FragmentActivitysBinding
import com.lhr.travel.recyclerViewAdapter.ActivitysAdapter
import com.lhr.travel.recyclerViewAdapter.NewsAdapter
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage

class ActivitysFragment: Fragment(), View.OnClickListener {
    
    lateinit var binding: FragmentActivitysBinding
    lateinit var viewModel: ActivitysViewModel
    lateinit var activityAdapter: ActivitysAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_activitys, container, false
        )
        val view: View = binding!!.root
        activityAdapter = ActivitysAdapter(this.requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ActivitysViewModelFactory(requireActivity().application)
        )[ActivitysViewModel::class.java]

        initRecyclerView(binding.recyclerActivitys)

        viewModel.isProgressVisible.observe(viewLifecycleOwner) { isLoading -> // 當isDialogLoading改變時觸發Dialog
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // 讀取資料時顯示轉圈
                binding.recyclerActivitys.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.GONE // 讀取資料完成隱藏轉圈
                binding.recyclerActivitys.visibility = View.VISIBLE
            }
        }

        currentLanguage.observe(viewLifecycleOwner) { newIds ->
            // 語言變更時執行的動作
            loadactivityApiData()
        }
        loadactivityApiData()
    }

    fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = activityAdapter
        }
    }

    fun loadactivityApiData() {
        viewModel.getActivitysObserver()
            .observe(viewLifecycleOwner, Observer<ActivitysResponse?> {
                if (it != null) {
                    //更新adapter
                    activityAdapter.listData = it.data
                    activityAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this.context, "Error in fetching data", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        viewModel.fetchActivitysApi()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

}