package com.noahutz.photobrowser.repository

import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.db.dao.PhotoDao
import com.noahutz.photobrowser.db.entity.PhotoEntity
import com.noahutz.photobrowser.model.Photo

class PhotoRepository(private val apiService: PhotoBrowserService, private val photoDao: PhotoDao) {

    suspend fun loadPhotos(albumId: Int): List<Photo> {
        apiService.getPhotos(albumId).forEach { photo ->
            photoDao.insertPhoto(
                photo = PhotoEntity(
                    id = photo.id,
                    albumId = photo.albumId,
                    thumbnailUrl = photo.thumbnailUrl,
                    title = photo.title,
                    url = photo.url
                )
            )
        }

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
}