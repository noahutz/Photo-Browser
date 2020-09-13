package com.noahutz.photobrowser.repository

import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.db.dao.AlbumDao
import com.noahutz.photobrowser.db.entity.AlbumEntity
import com.noahutz.photobrowser.model.Album

class AlbumRepository(
    private val apiService: PhotoBrowserService,
    private val albumDao: AlbumDao,
) {
    suspend fun loadAlbums(): List<Album> {
        apiService.getAlbums().forEach { album ->
            albumDao.insertAlbum(
                album = AlbumEntity(
                    id = album.id,
                    title = album.title,
                    userId = album.userId
                )
            )
        }

        return albumDao.getAlbums().map { album ->
            Album(id = album.id, title = album.title, userId = album.userId)
        }
    }
}
