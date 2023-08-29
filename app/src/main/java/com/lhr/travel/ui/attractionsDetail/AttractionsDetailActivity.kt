package com.lhr.travel.ui.attractionsDetail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.lhr.travel.R
import com.lhr.travel.data.attractions.Data
import com.lhr.travel.databinding.ActivityAttractionsDetailBinding
import com.lhr.travel.ui.web.WebActivity

class AttractionsDetailActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var viewModel: AttractionsDetailViewModel
    lateinit var binding: ActivityAttractionsDetailBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_attractions_detail)

        viewModel = ViewModelProvider(
            this,
            AttractionsDetailViewModelFactory(this.application)
        )[AttractionsDetailViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        var attractionsData = intent.getSerializableExtra("AttractionsData", Data::class.java)
        if (attractionsData != null) {
            viewModel.title.postValue(attractionsData.name)
            attractionsData.images.takeIf { it.isNotEmpty() }?.let {
                binding.imageAttractions.load(attractionsData.images[0].src)
            }
            viewModel.introduction.postValue(attractionsData.introduction)
            viewModel.address.postValue(attractionsData.address)
            viewModel.modified.postValue(attractionsData.modified)
            viewModel.url.postValue(attractionsData.url)
        }
        binding.textUrl.setOnClickListener(this)
        binding.imageBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textUrl -> {
                val intent = Intent(this, WebActivity::class.java)
                intent.putExtra("Title", viewModel.title.value)
                intent.putExtra("Url", viewModel.url.value)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.imageBack -> {
                finish()
            }
        }
    }

}