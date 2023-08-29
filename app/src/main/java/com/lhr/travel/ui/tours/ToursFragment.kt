package com.lhr.travel.ui.tours

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhr.travel.R
import com.lhr.travel.data.tours.ToursResponse
import com.lhr.travel.databinding.FragmentToursBinding
import com.lhr.travel.recyclerViewAdapter.ToursAdapter
import com.lhr.travel.popupWindow.LanguagePopupWindow
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage

class ToursFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentToursBinding
    lateinit var viewModel: ToursViewModel
lateinit var ToursAdapter: ToursAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tours, container, false
        )
        val view: View = binding!!.root
        ToursAdapter = ToursAdapter(this.requireActivity().applicationContext)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ToursViewModelFactory(requireActivity().application)
        )[ToursViewModel::class.java]

        initRecyclerView(binding.recyclerTours)

        viewModel.isProgressVisible.observe(viewLifecycleOwner) { isLoading -> // 當isDialogLoading改變時觸發Dialog
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // 讀取資料時顯示轉圈
                binding.recyclerTours.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.GONE // 讀取資料完成隱藏轉圈
                binding.recyclerTours.visibility = View.VISIBLE
            }
        }

        currentLanguage.observe(viewLifecycleOwner) { newIds ->
            // 語言變更時執行的動作
            loadToursApiData()
        }

        loadToursApiData()

        binding.imageLanguage.setOnClickListener(this)
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
            adapter = ToursAdapter
        }
    }

    fun loadToursApiData() {
        viewModel.getToursListObserver()
            .observe(viewLifecycleOwner, Observer<ToursResponse?> {
                if (it != null) {
                    //更新adapter
                    ToursAdapter.listData = it.data
                    ToursAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this.context, "Error in fetching data", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        viewModel.fetchToursApi()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageLanguage -> {
                val choose = LanguagePopupWindow(this.requireContext())
                val view: View = LayoutInflater.from(this.context).inflate(
                    R.layout.popup_window_language,
                    null
                )
                choose.showAtLocation(view, Gravity.CENTER, 0, 0)
                //強制隱藏鍵盤
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)

            }

        }
    }

}