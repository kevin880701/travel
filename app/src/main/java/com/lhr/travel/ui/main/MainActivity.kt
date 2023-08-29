package com.lhr.travel.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.lhr.travel.R
import com.lhr.travel.databinding.ActivityMainBinding
import com.lhr.travel.ui.attractions.AttractionsFragment
import com.lhr.travel.ui.events.EventsFragment
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage
import com.lhr.travel.ui.media.MediaFragment
import com.lhr.travel.ui.tours.ToursFragment
import com.lhr.travel.viewPager.MainViewPageAdapter

class MainActivity: AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var pageAdapter: MainViewPageAdapter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(this.application)
        )[MainViewModel::class.java]

        // 設置 currentLanguage 初始值(起始語言)
        currentLanguage.value = "zh-tw"

        initTabLayout(binding.tabLayoutMain)
    }

    fun initTabLayout(tabLayoutMain: TabLayout) {
        tabLayoutMain.apply {
            var tabIconList = arrayListOf(
                R.drawable.attractions,
                R.drawable.event,
                R.drawable.media,
                R.drawable.tours
            )
            var fragments = arrayListOf(
                AttractionsFragment(),
                EventsFragment(),
                MediaFragment(),
                ToursFragment()
            ) as ArrayList<Fragment>
            pageAdapter = MainViewPageAdapter(supportFragmentManager, lifecycle, fragments)
            binding.viewPager.adapter = pageAdapter
            TabLayoutMediator(this, binding.viewPager) { tab, position ->
                tab.icon = ContextCompat.getDrawable(this.context, tabIconList[position])
            }.attach()
            binding.viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        }
    }
}