package com.noahutz.photobrowser.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.noahutz.photobrowser.model.Photo
import com.noahutz.photobrowser.repository.PhotoRepository

class PhotoListViewModel @ViewModelInject constructor(
    private val repository: PhotoRepository
) : ViewModel() {
    fun getPhotos(albumId: Int): LiveData<List<Photo>> = liveData {
        emit(repository.getPhotos(albumId))
    }
}
