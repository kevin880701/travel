package com.lhr.travel.ui.media

import android.content.Context
import android.media.MediaPlayer
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
import com.lhr.travel.databinding.FragmentMediaBinding
import com.lhr.travel.popupWindow.LanguagePopupWindow
import com.lhr.travel.ui.media.MediaViewModel.Companion.currentMediaId
import com.lhr.travel.ui.media.audio.AudioFragment
import com.lhr.travel.ui.media.panos.PanosFragment
import com.lhr.travel.viewPager.MediaViewPageAdapter

class MediaFragment : Fragment(), View.OnClickListener {
    lateinit var viewModel: MediaViewModel
    lateinit var binding: FragmentMediaBinding
    private var mediaPlayer: MediaPlayer? = null
    lateinit var mediaViewPageAdapter: MediaViewPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media, container, false)
        val view: View = binding!!.root
        viewModel = ViewModelProvider(
            this,
            MediaViewModelFactory(requireActivity().application)
        )[MediaViewModel::class.java]


        initTabLayout(binding.tabLayoutMedia)

        binding.imageLanguage.setOnClickListener(this)
        return view
    }

    fun initTabLayout(tabLayoutMedia: TabLayout) {
        tabLayoutMedia.apply {
            var fragments = arrayListOf(
                PanosFragment(),
                AudioFragment()
            ) as ArrayList<Fragment>
            mediaViewPageAdapter = MediaViewPageAdapter(
                requireActivity().supportFragmentManager,
                lifecycle,
                fragments
            )
            binding.viewPageMedia!!.adapter = mediaViewPageAdapter
            TabLayoutMediator(tabLayoutMedia, binding.viewPageMedia, true) { tab, position ->
                binding.viewPageMedia!!.currentItem = tab.position
            }.attach()
            val mediaTab = requireActivity().resources.getStringArray(R.array.media_tab)
            for (i in 0 until tabLayoutMedia.tabCount) {
                tabLayoutMedia.getTabAt(i)!!.text = mediaTab[i].toString()
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