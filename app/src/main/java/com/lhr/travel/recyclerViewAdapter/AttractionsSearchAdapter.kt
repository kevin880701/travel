package com.lhr.travel.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lhr.travel.R
import com.lhr.travel.ui.attractions.AttractionsFragment
import com.lhr.travel.ui.attractions.AttractionsViewModel.Companion.currentCategoryIds

//import com.lhr.travel.model.Model.Companion.attractionsFragment
//import com.lhr.travel.model.Model.Companion.currentCategoryIds

class AttractionsSearchAdapter(fragment: AttractionsFragment, popupWindow: PopupWindow) : RecyclerView.Adapter<AttractionsSearchAdapter.ViewHolder>() {
    var categoryList = fragment.requireContext().resources.getStringArray(R.array.category_array)
    var categoryIdList = fragment.requireContext().resources.getStringArray(R.array.category_id_array)
    var popupWindow = popupWindow

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attractions_search, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textCategoryName.text = categoryList[position]

        holder.itemView.setOnClickListener {
            currentCategoryIds.value = categoryIdList[position]
//            Model.attractionsFragment.loadAttractionsApiData()
            popupWindow.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textCategoryName: TextView = v.findViewById(R.id.textCategoryName)
    }
}