package com.noahutz.photobrowser.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.noahutz.photobrowser.model.Photo
import com.noahutz.photobrowser.repository.PhotoRepository
import com.noahutz.photobrowser.util.ResultOf

class PhotoListViewModel @ViewModelInject constructor(
    private val repository: PhotoRepository
) : ViewModel() {
    fun getPhotos(albumId: Int): LiveData<ResultOf<List<Photo>>> = repository.getPhotos(albumId)
}
