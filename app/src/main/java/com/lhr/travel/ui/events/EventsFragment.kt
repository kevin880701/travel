package com.lhr.travel.ui.events

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lhr.travel.R
import com.lhr.travel.viewPager.EventsViewPageAdapter
import com.lhr.travel.databinding.FragmentEventsBinding
import com.lhr.travel.ui.events.calendar.CalendarFragment
import com.lhr.travel.ui.events.news.NewsFragment
import com.lhr.travel.popupWindow.LanguagePopupWindow
import com.lhr.travel.ui.events.activitys.ActivitysFragment

class EventsFragment : Fragment(), View.OnClickListener {
    lateinit var viewModel: EventsViewModel
    lateinit var binding: FragmentEventsBinding
    lateinit var eventsViewPageAdapter: EventsViewPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false)
        val view: View = binding!!.root
        viewModel = ViewModelProvider(
            this,
            EventsViewModelFactory(requireActivity().application)
        )[EventsViewModel::class.java]

        initTabLayout(binding.tabLayoutEvents)

        binding.imageLanguage.setOnClickListener(this)
        return view
    }

    fun initTabLayout(tabLayoutEvents: TabLayout) {
        tabLayoutEvents.apply {
            var fragments = arrayListOf(
                NewsFragment(),
                ActivitysFragment(),
                CalendarFragment()
            ) as ArrayList<Fragment>
            eventsViewPageAdapter = EventsViewPageAdapter(
                requireActivity().supportFragmentManager,
                lifecycle,
                fragments
            )
            binding.viewPageEvents!!.adapter = eventsViewPageAdapter
            TabLayoutMediator(tabLayoutEvents, binding.viewPageEvents, true) { tab, position ->
                binding.viewPageEvents!!.currentItem = tab.position
            }.attach()
            val eventsTab = requireActivity().resources.getStringArray(R.array.events_tab)
            for (i in 0 until tabLayoutEvents.tabCount) {
                tabLayoutEvents.getTabAt(i)!!.text = eventsTab[i].toString()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageLanguage -> {
                val choose = LanguagePopupWindow(this.requireContext())
                val view: View = LayoutInflater.from(this.context).inflate(
                    R.layout.popup_window_attractions_search,
                    null
                )
                choose.showAtLocation(view, Gravity.CENTER, 0, 0)
                //強制隱藏鍵盤
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)
            }
        }
    }
}