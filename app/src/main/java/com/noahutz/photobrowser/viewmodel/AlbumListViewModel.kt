package com.noahutz.photobrowser.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.noahutz.photobrowser.model.Album
import com.noahutz.photobrowser.repository.AlbumRepository
import com.noahutz.photobrowser.util.ResultOf

class AlbumListViewModel @ViewModelInject constructor(
    private val repository: AlbumRepository
) : ViewModel() {
    fun getAlbums(): LiveData<ResultOf<List<Album>>> = repository.getAlbums()
}
