package com.noahutz.photobrowser.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.noahutz.photobrowser.R
import com.noahutz.photobrowser.util.ImageLoader
import com.noahutz.photobrowser.viewmodel.PhotoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_photo.*
import javax.inject.Inject

@AndroidEntryPoint
class PhotoListFragment : Fragment() {
    companion object {
        const val GRID_SPAN_COUNT = 2
        const val ARG_ALBUM_ID = "album_id"
    }

    @Inject
    lateinit var imageLoader: ImageLoader

    private var albumId: Int = -1
    private val viewModel: PhotoListViewModel by viewModels()
    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumId = arguments?.getInt(ARG_ALBUM_ID)
            ?: throw IllegalArgumentException("Expected argument: $ARG_ALBUM_ID")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(context, GRID_SPAN_COUNT)
        adapter = PhotoAdapter(imageLoader)
        recyclerViewPhotos.layoutManager = layoutManager
        recyclerViewPhotos.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            loadPhotoList()
        }

        swipeRefreshLayout.isRefreshing = true
        loadPhotoList()
    }

    private fun loadPhotoList() {
        viewModel.getPhotos(albumId).observe(viewLifecycleOwner) { photos ->
            swipeRefreshLayout.isRefreshing = false
            adapter.setItems(photos)
        }
    }
}