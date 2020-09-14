package com.noahutz.photobrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.api.PhotoResponse
import com.noahutz.photobrowser.db.dao.PhotoDao
import com.noahutz.photobrowser.db.entity.PhotoEntity
import com.noahutz.photobrowser.model.Photo
import com.noahutz.photobrowser.util.ApiHelper
import com.noahutz.photobrowser.util.ResultOf
import com.noahutz.photobrowser.util.doIfFailure
import com.noahutz.photobrowser.util.doIfSuccess
import com.noahutz.photobrowser.util.map
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val apiService: PhotoBrowserService,
    private val photoDao: PhotoDao
) {
    fun getPhotos(albumId: Int): LiveData<ResultOf<List<Photo>>> = liveData {
        // Fetch from API
        val resultApi = ApiHelper.getResult { apiService.getPhotos(albumId) }

        resultApi.map { it.toListEntity() }.doIfSuccess { entityPhotos ->
            // Save to DB
            photoDao.insertPhotos(entityPhotos)

            // Load from DB
            val data = loadFromDB(albumId)
            emit(ResultOf.Success(data))
        }

        resultApi.doIfFailure { _, throwable ->
            throwable?.printStackTrace()

            // Fetch from API failed, try to load from database
            val data = loadFromDB(albumId)
            emit(ResultOf.Success(data))
        }
    }

    private suspend fun loadFromDB(albumId: Int): List<Photo> {
        return photoDao.getPhotos(albumId).map { photo ->
            Photo(
                id = photo.id,
                albumId = photo.albumId,
                thumbnailUrl = photo.thumbnailUrl,
                title = photo.title,
                url = photo.url
            )
        }
    }

    private fun List<PhotoResponse>.toListEntity(): List<PhotoEntity> {
        return map { photo ->
            PhotoEntity(
                id = photo.id,
                albumId = photo.albumId,
                thumbnailUrl = photo.thumbnailUrl,
                title = photo.title,
                url = photo.url
            )
        }
    }
}
