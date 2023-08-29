package com.lhr.travel.ui.events.news

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
import com.lhr.travel.data.news.NewsResponse
import com.lhr.travel.databinding.FragmentNewsBinding
import com.lhr.travel.recyclerViewAdapter.NewsAdapter
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage

class NewsFragment: Fragment(), View.OnClickListener {
    
    lateinit var binding: FragmentNewsBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_news, container, false
        )
        val view: View = binding!!.root
        newsAdapter = NewsAdapter(this.requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(requireActivity().application)
        )[NewsViewModel::class.java]

        initRecyclerView(binding.recyclerNews)

        viewModel.isProgressVisible.observe(viewLifecycleOwner) { isLoading -> // 當isDialogLoading改變時觸發Dialog
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // 讀取資料時顯示轉圈
                binding.recyclerNews.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.GONE // 讀取資料完成隱藏轉圈
                binding.recyclerNews.visibility = View.VISIBLE
            }
        }

        currentLanguage.observe(viewLifecycleOwner) { newIds ->
            // 語言變更時執行的動作
            loadNewsApiData()
        }
        loadNewsApiData()
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
            adapter = newsAdapter
        }
    }

    fun loadNewsApiData() {
        viewModel.getNewsObserver()
            .observe(viewLifecycleOwner, Observer<NewsResponse?> {
                if (it != null) {
                    //更新adapter
                    newsAdapter.listData = it.data
                    newsAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this.context, "Error in fetching data", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        viewModel.fetchNewsApi()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

}