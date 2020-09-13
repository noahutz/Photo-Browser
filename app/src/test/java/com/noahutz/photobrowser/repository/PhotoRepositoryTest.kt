package com.noahutz.photobrowser.repository

import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.api.PhotoResponse
import com.noahutz.photobrowser.db.dao.PhotoDao
import com.noahutz.photobrowser.db.entity.PhotoEntity
import com.noahutz.photobrowser.model.Photo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldContainAll
import org.junit.Test

class PhotoRepositoryTest {

    private val apiService: PhotoBrowserService = mockk()
    private val photoDao: PhotoDao = spyk()

    private val repository: PhotoRepository =
        PhotoRepository(apiService = apiService, photoDao = photoDao)

    @Test
    fun `load photos retrieves data from api and store to database`() {
        val albumId = 1
        val photos = (0 until 10).map { id ->
            Photo(
                id = id,
                albumId = albumId,
                thumbnailUrl = "Thumbnail url $id",
                title = "Title $id",
                url = "some url $id"
            )
        }
        val serviceList = photos.map { photo ->
            PhotoResponse(
                id = photo.id,
                albumId = photo.albumId,
                thumbnailUrl = photo.thumbnailUrl,
                title = photo.title,
                url = photo.url
            )
        }
        val databaseList = photos.map { photo ->
            PhotoEntity(
                id = photo.id,
                albumId = photo.albumId,
                thumbnailUrl = photo.thumbnailUrl,
                title = photo.title,
                url = photo.url
            )
        }
        coEvery { apiService.getPhotos(albumId) } returns serviceList
        coEvery { photoDao.getPhotos(albumId) } returns databaseList

        val result = runBlocking { repository.getPhotos(albumId) }

        result shouldContainAll photos
        databaseList.forEach { photo ->
            coVerify { photoDao.insertPhoto(photo) }
        }
    }
}