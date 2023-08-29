package com.lhr.travel.ui.media.panos

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
import com.lhr.travel.data.panos.PanosResponse
import com.lhr.travel.databinding.FragmentPanosBinding
import com.lhr.travel.recyclerViewAdapter.PanosAdapter
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage
import com.lhr.travel.ui.media.panos.PanosViewModelFactory

class PanosFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentPanosBinding
    lateinit var viewModel: PanosViewModel
    lateinit var panosAdapter: PanosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_panos, container, false
        )
        val view: View = binding!!.root
        panosAdapter = PanosAdapter(this.requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            PanosViewModelFactory(requireActivity().application)
        )[PanosViewModel::class.java]

        initRecyclerView(binding.recyclerPanos)

        viewModel.isProgressVisible.observe(viewLifecycleOwner) { isLoading -> // 當isDialogLoading改變時觸發Dialog
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // 讀取資料時顯示轉圈
                binding.recyclerPanos.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.GONE // 讀取資料完成隱藏轉圈
                binding.recyclerPanos.visibility = View.VISIBLE
            }
        }

        currentLanguage.observe(viewLifecycleOwner) { newIds ->
            // 語言變更時執行的動作
            loadPanosApiData()
        }

        loadPanosApiData()
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
            adapter = panosAdapter
        }
    }

    fun loadPanosApiData() {
        viewModel.getPanosListObserver()
            .observe(viewLifecycleOwner, Observer<PanosResponse?> {
                if (it != null) {
                    //更新adapter
                    panosAdapter.listData = it.data
                    panosAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this.context, "Error in fetching data", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        viewModel.fetchPanosApi()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

}