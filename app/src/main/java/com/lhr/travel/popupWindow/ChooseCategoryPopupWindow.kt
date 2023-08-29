package com.lhr.travel.popupWindow

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhr.travel.R
import com.lhr.travel.recyclerViewAdapter.AttractionsSearchAdapter
import com.lhr.travel.ui.attractions.AttractionsFragment

class ChooseCategoryPopupWindow (fragment: AttractionsFragment) : PopupWindow(), View.OnClickListener {
    val fragment = fragment
    var view: View
    var recyclerCategory: RecyclerView
    lateinit var attractionsSearchAdapter: AttractionsSearchAdapter

    init {
        view = LayoutInflater.from(fragment.context).inflate(R.layout.popup_window_attractions_search, null)
        recyclerCategory = view.findViewById(R.id.recyclerCategory)

        attractionsSearchAdapter = AttractionsSearchAdapter(fragment, this)

        initRecyclerView()
        // 外部可點擊
        this.isOutsideTouchable = true
        // mMenuView添加OnTouchListener監聽判斷獲取點擊位置如果在選擇框外面則銷毀彈出框
        view.setOnTouchListener { v, event ->
            val height = view.findViewById<View>(R.id.popLayout).top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss()
                }
            }
            true
        }
        this.contentView = view
        // 視窗高和寬填滿
        this.height = RelativeLayout.LayoutParams.MATCH_PARENT
        this.width = RelativeLayout.LayoutParams.MATCH_PARENT
        // 設置彈出視窗可點擊
        this.isFocusable = true
        // 背景色
        val dw = ColorDrawable(-0x50000000)
        setBackgroundDrawable(dw)
        // 彈出的動畫
        this.animationStyle = R.style.take_photo_anim

    }


    fun initRecyclerView(){
        recyclerCategory.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = attractionsSearchAdapter
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }
}