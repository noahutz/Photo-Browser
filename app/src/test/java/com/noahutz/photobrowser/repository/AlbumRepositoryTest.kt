package com.noahutz.photobrowser.repository

import androidx.lifecycle.Observer
import com.noahutz.photobrowser.api.AlbumResponse
import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.db.dao.AlbumDao
import com.noahutz.photobrowser.db.entity.AlbumEntity
import com.noahutz.photobrowser.model.Album
import com.noahutz.photobrowser.util.ResultOf
import com.noahutz.photobrowser.base.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

class AlbumRepositoryTest : BaseTest() {
    companion object {
        private val MODEL_ALBUMS =
            (0 until 10).map { id -> Album(id = id, title = "Title $id", userId = 1) }
        private val API_ALBUMS = MODEL_ALBUMS.map { album ->
            AlbumResponse(
                id = album.id,
                title = album.title,
                userId = album.userId
            )
        }
        private val DB_ALBUMS = MODEL_ALBUMS.map { album ->
            AlbumEntity(
                id = album.id,
                title = album.title,
                userId = album.userId
            )
        }
    }

    private val albumsObserver: Observer<ResultOf<List<Album>>> = spyk()
    private val apiService: PhotoBrowserService = mockk()
    private val albumDao: AlbumDao = spyk()

    private val repository: AlbumRepository =
        AlbumRepository(apiService = apiService, albumDao = albumDao)

    @Test
    fun `load albums api success should save and retrieve from database`() {
        coEvery { apiService.getAlbums() } returns Response.success(API_ALBUMS)
        coEvery { albumDao.getAlbums() } returns DB_ALBUMS

        repository.getAlbums().observeForever(albumsObserver)

        albumsObserver.onChanged(ResultOf.Success(MODEL_ALBUMS))
        coVerify { apiService.getAlbums() }
        coVerify { albumDao.insertAlbums(DB_ALBUMS) }
    }

    @Test
    fun `load albums api fail should retrieve from database`() {
        coEvery { apiService.getAlbums() } returns
                Response.error(400, "Some error".toResponseBody())
        coEvery { albumDao.getAlbums() } returns DB_ALBUMS

        repository.getAlbums().observeForever(albumsObserver)

        albumsObserver.onChanged(ResultOf.Success(MODEL_ALBUMS))
        coVerify { albumDao.getAlbums() }
        coVerify(exactly = 0) { albumDao.insertAlbums(DB_ALBUMS) }
    }
}
