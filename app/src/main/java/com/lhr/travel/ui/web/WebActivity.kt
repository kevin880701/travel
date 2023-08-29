package com.lhr.travel.ui.web

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import coil.load
import com.lhr.travel.R
import com.lhr.travel.databinding.ActivityWebBinding


class WebActivity: AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web)
        var webSettings = binding.webView.settings
        webSettings.setJavaScriptEnabled(true) //開啟javascript功能

        binding.webView.webViewClient = WebViewClient() //新增瀏覽器客戶端
        intent.getStringExtra("Title").takeIf { it!!.isNotEmpty() }?.let {
            binding.textTitleBar.text = it
        }
        intent.getStringExtra("Url").takeIf { it!!.isNotEmpty() }?.let {
            binding.webView.loadUrl(it) //讀取url網站
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageBack -> {
                finish()
            }
        }
    }
}