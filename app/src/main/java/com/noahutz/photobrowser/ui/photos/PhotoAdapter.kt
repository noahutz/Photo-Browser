package com.noahutz.photobrowser.ui.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noahutz.photobrowser.R
import com.noahutz.photobrowser.model.Photo
import com.noahutz.photobrowser.util.ImageLoader
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    private val items: ArrayList<Photo> = arrayListOf()

    fun setItems(items: List<Photo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Photo) {
            imageLoader.loadImage(item.thumbnailUrl, itemView.imageViewThumbnail)
            itemView.textViewTitle.text = item.title
        }
    }
}