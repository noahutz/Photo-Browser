package com.noahutz.photobrowser.repository

import com.noahutz.photobrowser.api.AlbumResponse
import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.db.dao.AlbumDao
import com.noahutz.photobrowser.db.entity.AlbumEntity
import com.noahutz.photobrowser.model.Album
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldContainAll
import org.junit.Test

class AlbumRepositoryTest {

    private val apiService: PhotoBrowserService = mockk()
    private val albumDao: AlbumDao = spyk()

    private val repository: AlbumRepository =
        AlbumRepository(apiService = apiService, albumDao = albumDao)

    @Test
    fun `load albums retrieves data from api and store to database`() {
        val albums = (0 until 10).map { id -> Album(id = id, title = "Title $id", userId = 1) }
        val serviceList = albums.map { album ->
            AlbumResponse(
                id = album.id,
                title = album.title,
                userId = album.userId
            )
        }
        val databaseList = albums.map { album ->
            AlbumEntity(
                id = album.id,
                title = album.title,
                userId = album.userId
            )
        }
        coEvery { apiService.getAlbums() } returns serviceList
        coEvery { albumDao.getAlbums() } returns databaseList

        val result = runBlocking { repository.loadAlbums() }

        result shouldContainAll albums
        databaseList.forEach { album ->
            coVerify { albumDao.insertAlbum(album) }
        }
    }
}
