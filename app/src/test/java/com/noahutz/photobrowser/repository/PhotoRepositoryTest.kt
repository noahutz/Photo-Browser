package com.noahutz.photobrowser.repository

import androidx.lifecycle.Observer
import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.api.PhotoResponse
import com.noahutz.photobrowser.base.BaseTest
import com.noahutz.photobrowser.db.dao.PhotoDao
import com.noahutz.photobrowser.db.entity.PhotoEntity
import com.noahutz.photobrowser.model.Photo
import com.noahutz.photobrowser.util.ResultOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

class PhotoRepositoryTest : BaseTest() {
    companion object {
        private const val ALBUM_ID = 1
        private val MODEL_PHOTOS = (0 until 10).map { id ->
            Photo(
                id = id,
                albumId = ALBUM_ID,
                thumbnailUrl = "Thumbnail url $id",
                title = "Title $id",
                url = "some url $id"
            )
        }
        private val API_PHOTOS = MODEL_PHOTOS.map { photo ->
            PhotoResponse(
                id = photo.id,
                albumId = photo.albumId,
                thumbnailUrl = photo.thumbnailUrl,
                title = photo.title,
                url = photo.url
            )
        }
        private val DB_PHOTOS = MODEL_PHOTOS.map { photo ->
            PhotoEntity(
                id = photo.id,
                albumId = photo.albumId,
                thumbnailUrl = photo.thumbnailUrl,
                title = photo.title,
                url = photo.url
            )
        }
    }

    private val photosObserver: Observer<ResultOf<List<Photo>>> = spyk()
    private val apiService: PhotoBrowserService = mockk()
    private val photoDao: PhotoDao = spyk()

    private val repository: PhotoRepository =
        PhotoRepository(apiService = apiService, photoDao = photoDao)

    @Test
    fun `load photos api success should save and retrieve from database`() {
        val albumId = 1
        coEvery { apiService.getPhotos(albumId) } returns Response.success(API_PHOTOS)
        coEvery { photoDao.getPhotos(albumId) } returns DB_PHOTOS

        repository.getPhotos(albumId).observeForever(photosObserver)

        photosObserver.onChanged(ResultOf.Success(MODEL_PHOTOS))
        coVerify { apiService.getPhotos(ALBUM_ID) }
        coVerify { photoDao.insertPhotos(DB_PHOTOS) }
    }

    @Test
    fun `load photos api fail should retrieve from database`() {
        coEvery { apiService.getPhotos(ALBUM_ID) } returns
                Response.error(400, "Some error".toResponseBody())
        coEvery { photoDao.getPhotos(ALBUM_ID) } returns DB_PHOTOS

        repository.getPhotos(ALBUM_ID).observeForever(photosObserver)

        photosObserver.onChanged(ResultOf.Success(MODEL_PHOTOS))
        coVerify { apiService.getPhotos(ALBUM_ID) }
        coVerify(exactly = 0) { photoDao.insertPhotos(DB_PHOTOS) }
    }
}
