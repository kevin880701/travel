package com.lhr.travel.ui.attractions

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.lhr.travel.popupWindow.ChooseCategoryPopupWindow
import com.lhr.travel.data.attractions.AttractionsResponse
import com.lhr.travel.R
import com.lhr.travel.recyclerViewAdapter.AttractionsAdapter
import com.lhr.travel.databinding.FragmentAttractionsBinding
import com.lhr.travel.ui.attractions.AttractionsViewModel.Companion.currentCategoryIds
import com.lhr.travel.popupWindow.LanguagePopupWindow
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage
import com.lhr.travel.ui.media.MediaViewModel.Companion.previousMediaId
import java.util.Locale

class AttractionsFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentAttractionsBinding
    lateinit var viewModel: AttractionsViewModel
lateinit var attractionsAdapter: AttractionsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_attractions, container, false
        )
        val view: View = binding!!.root

        viewModel = ViewModelProvider(
            this,
            AttractionsViewModelFactory(requireActivity().application)
        )[AttractionsViewModel::class.java]
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        viewModel.title.postValue(this.requireContext().resources.getString(R.string.attraction))
        attractionsAdapter = AttractionsAdapter(this.requireActivity().applicationContext)
        initRecyclerView(binding.recyclerAttractions)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 設置 currentCategoryIds 初始值
        currentCategoryIds.value = ""

        viewModel.isProgressVisible.observe(viewLifecycleOwner) { isLoading -> // 當isDialogLoading改變時觸發Dialog
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // 讀取資料時顯示轉圈
                binding.recyclerAttractions.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.GONE // 讀取資料完成隱藏轉圈
                binding.recyclerAttractions.visibility = View.VISIBLE
            }
        }

        currentCategoryIds.observe(viewLifecycleOwner) { newIds ->
            // currentCategoryIds 值變更時執行的動作
            loadAttractionsApiData()
        }

        currentLanguage.observe(viewLifecycleOwner) { newLanguageId ->
            // 語言變更時執行的動作
            changeLanguage(newLanguageId)
            loadAttractionsApiData()
        }

        loadAttractionsApiData()

        binding.imageSearch.setOnClickListener(this)
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
            adapter = attractionsAdapter
        }
    }

    fun loadAttractionsApiData() {
        viewModel.getAttractionsListObserver()
            .observe(viewLifecycleOwner, Observer<AttractionsResponse?> {
                if (it != null) {
                    //更新adapter
                    attractionsAdapter.listData = it.data
                    attractionsAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this.context, "Error in fetching data", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        viewModel.fetchAttractionsApi()
    }

    fun changeLanguage(newLanguageId: String){
        when(newLanguageId){
            "zh-tw" -> {
                val locale = Locale.TAIWAN
//                    val locale = Locale("zh")
                val configuration = resources.configuration
                configuration.setLocale(locale)
                val localizedContext = this.requireActivity().createConfigurationContext(configuration)
                viewModel.title.postValue(localizedContext.resources.getString(R.string.attraction))
            }
            "en" -> {
                val locale = Locale("en")
                val configuration = resources.configuration
                configuration.setLocale(locale)
                val localizedContext = this.requireActivity().createConfigurationContext(configuration)
                viewModel.title.postValue(localizedContext.resources.getString(R.string.attraction))
            }
            "zh-cn" -> {
                val locale = Locale.CHINA
//                    val locale = Locale("zh","rCN")
                val configuration = resources.configuration
                configuration.setLocale(locale)
                val localizedContext = this.requireActivity().createConfigurationContext(configuration)
                viewModel.title.postValue(localizedContext.resources.getString(R.string.attraction))
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageSearch -> {
                val choose = ChooseCategoryPopupWindow(this)
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

            R.id.imageLanguage -> {
                val choose = LanguagePopupWindow(this.requireContext())
                val view: View = LayoutInflater.from(this.context).inflate(
                    R.layout.popup_window_attractions_search,
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