package com.noahutz.photobrowser.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.noahutz.photobrowser.R
import com.noahutz.photobrowser.model.Album
import kotlinx.android.synthetic.main.fragment_list_album.*

class AlbumListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AlbumAdapter {

        }
        recyclerViewAlbums.setHasFixedSize(true)
        recyclerViewAlbums.layoutManager = LinearLayoutManager(context)
        recyclerViewAlbums.adapter = adapter

        adapter.setItems((0..10).map { Album(id = it, title = "Title $it", userId = 1) })
    }
}