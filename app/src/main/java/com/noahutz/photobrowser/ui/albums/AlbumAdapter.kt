package com.noahutz.photobrowser.ui.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noahutz.photobrowser.R
import com.noahutz.photobrowser.model.Album
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumAdapter(
    private val itemClickListener: (item: Album) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
    private val items: ArrayList<Album> = arrayListOf()

    fun setItems(items: List<Album>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Album) {
            itemView.textViewTitle.text = item.title
            itemView.setOnClickListener { itemClickListener(item) }
        }
    }
}