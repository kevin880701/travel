package com.lhr.travel.recyclerViewAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lhr.travel.R
import com.lhr.travel.data.attractions.Data
import com.lhr.travel.ui.attractionsDetail.AttractionsDetailActivity

class AttractionsAdapter(context: Context) : RecyclerView.Adapter<AttractionsAdapter.ViewHolder>() {
    var context = context
    var listData = ArrayList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attractions, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listData[position].images.takeIf { it.isNotEmpty() }?.let {
            holder.imageAttractions.load(listData[position].images[0].src)
        }
        holder.textTitle.text = listData[position].name
        holder.textIntroduction.text = listData[position].introduction
        holder.itemView.setOnClickListener {
            val intent = Intent(context, AttractionsDetailActivity::class.java)
            intent.putExtra("AttractionsData", listData[position])
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imageAttractions: ImageView = v.findViewById(R.id.imageAttractions)
        val textTitle: TextView = v.findViewById(R.id.textTitle)
        val textIntroduction: TextView = v.findViewById(R.id.textIntroduction)
    }
}