package com.lhr.travel.ui.events.calendar

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
import com.lhr.travel.data.calendar.CalendarResponse
import com.lhr.travel.databinding.FragmentCalendarBinding
import com.lhr.travel.recyclerViewAdapter.CalendarAdapter
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage

class CalendarFragment: Fragment(), View.OnClickListener {
    
    lateinit var binding: FragmentCalendarBinding
    lateinit var viewModel: CalendarViewModel
    lateinit var calendarAdapter: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_calendar, container, false
        )
        val view: View = binding!!.root
        calendarAdapter = CalendarAdapter(this.requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            CalendarViewModelFactory(requireActivity().application)
        )[CalendarViewModel::class.java]

        initRecyclerView(binding.recyclerCalendar)

        viewModel.isProgressVisible.observe(viewLifecycleOwner) { isLoading -> // 當isDialogLoading改變時觸發Dialog
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // 讀取資料時顯示轉圈
                binding.recyclerCalendar.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.GONE // 讀取資料完成隱藏轉圈
                binding.recyclerCalendar.visibility = View.VISIBLE
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
            adapter = calendarAdapter
        }
    }

    fun loadactivityApiData() {
        viewModel.getCalendarObserver()
            .observe(viewLifecycleOwner, Observer<CalendarResponse?> {
                if (it != null) {
                    //更新adapter
                    calendarAdapter.listData = it.data
                    calendarAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this.context, "Error in fetching data", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        viewModel.fetchCalendarApi()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

}