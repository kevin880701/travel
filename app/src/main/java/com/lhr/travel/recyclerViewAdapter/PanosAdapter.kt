package com.lhr.travel.recyclerViewAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lhr.travel.R
import com.lhr.travel.data.panos.Data
import com.lhr.travel.ui.web.WebActivity

class PanosAdapter(context: Context) : RecyclerView.Adapter<PanosAdapter.ViewHolder>() {
    var context = context
    var listData = ArrayList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_panos, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = listData[position].title
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("Title", listData[position].title)
            intent.putExtra("Url", listData[position].url)
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
        val textTitle: TextView = v.findViewById(R.id.textTitle)
    }
}