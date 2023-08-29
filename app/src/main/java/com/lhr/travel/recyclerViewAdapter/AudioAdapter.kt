package com.lhr.travel.recyclerViewAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lhr.travel.R
import com.lhr.travel.data.audio.Data
import com.lhr.travel.ui.media.MediaViewModel
import com.lhr.travel.ui.media.MediaViewModel.Companion.currentMediaId
import com.lhr.travel.ui.media.MediaViewModel.Companion.isPlaying
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView


class AudioAdapter(context: Context) : RecyclerView.Adapter<AudioAdapter.ViewHolder>() {
    var context = context
    var listData = ArrayList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_audio, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = listData[position].title
        val gifFromResource = GifDrawable(context.resources, R.drawable.playing)
        if (currentMediaId.value == position) {
            if (isPlaying.value!!) {
                gifFromResource.start()

            } else {
                gifFromResource.stop()
            }
            holder.gifImageViewPlaying.setImageDrawable(gifFromResource)
            holder.gifImageViewPlaying.visibility = View.VISIBLE
        } else {
            gifFromResource.recycle()
            holder.gifImageViewPlaying.visibility = View.INVISIBLE
        }
        holder.itemView.setOnClickListener {
            isPlaying.value = true
            currentMediaId.value = position
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
        val gifImageViewPlaying: GifImageView = v.findViewById(R.id.gifImageViewPlaying)
    }
}