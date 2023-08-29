package com.lhr.travel.recyclerViewAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lhr.travel.R
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage

class LanguageAdapter(context: Context, popupWindow: PopupWindow) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {
    var languageList = context.resources.getStringArray(R.array.language_array)
    var languageIdList = context.resources.getStringArray(R.array.language_id_array)
    var popupWindow = popupWindow

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attractions_search, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textCategoryName.text = languageList[position]

        holder.itemView.setOnClickListener {
            currentLanguage.value = languageIdList[position]
//            Model.attractionsFragment.loadAttractionsApiData()
            popupWindow.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textCategoryName: TextView = v.findViewById(R.id.textCategoryName)
    }
}