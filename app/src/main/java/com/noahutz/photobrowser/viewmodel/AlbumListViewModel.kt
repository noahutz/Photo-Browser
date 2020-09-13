package com.noahutz.photobrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.noahutz.photobrowser.model.Album
import com.noahutz.photobrowser.repository.AlbumRepository

class AlbumListViewModel(private val repository: AlbumRepository) : ViewModel() {
    fun getAlbums(): LiveData<List<Album>> = liveData {
        emit(repository.getAlbums())
    }
}