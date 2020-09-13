package com.noahutz.photobrowser.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.noahutz.photobrowser.R
import com.noahutz.photobrowser.model.Album
import com.noahutz.photobrowser.ui.photos.PhotoListFragment
import com.noahutz.photobrowser.viewmodel.AlbumListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_album.*

@AndroidEntryPoint
class AlbumListFragment : Fragment() {
    private val viewModel: AlbumListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        val adapter = AlbumAdapter { album -> navigateToPhotoList(album) }
        recyclerViewAlbums.layoutManager = layoutManager
        recyclerViewAlbums.adapter = adapter
        recyclerViewAlbums.addItemDecoration(
            DividerItemDecoration(context, layoutManager.orientation)
        )

        viewModel.getAlbums().observe(viewLifecycleOwner) { albums -> adapter.setItems(albums) }
    }

    private fun navigateToPhotoList(album: Album) {
        findNavController().navigate(
            R.id.action_AlbumListFragment_to_PhotoListFragment,
            bundleOf(PhotoListFragment.ARG_ALBUM_ID to album.id)
        )
    }
}