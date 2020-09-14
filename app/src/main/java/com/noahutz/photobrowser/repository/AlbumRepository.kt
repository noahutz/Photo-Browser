package com.noahutz.photobrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.noahutz.photobrowser.api.AlbumResponse
import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.db.dao.AlbumDao
import com.noahutz.photobrowser.db.entity.AlbumEntity
import com.noahutz.photobrowser.model.Album
import com.noahutz.photobrowser.util.ApiHelper
import com.noahutz.photobrowser.util.ResultOf
import com.noahutz.photobrowser.util.doIfFailure
import com.noahutz.photobrowser.util.doIfSuccess
import com.noahutz.photobrowser.util.map
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val apiService: PhotoBrowserService,
    private val albumDao: AlbumDao,
) {
    fun getAlbums(): LiveData<ResultOf<List<Album>>> = liveData {
        // Fetch from API
        val resultApi = ApiHelper.getResult { apiService.getAlbums() }

        resultApi.map { it.toListEntity() }.doIfSuccess { entityAlbums ->
            // Save to DB
            albumDao.insertAlbums(entityAlbums)

            // Load from DB
            val data = loadFromDB()
            emit(ResultOf.Success(data))
        }

        resultApi.doIfFailure { _, throwable ->
            throwable?.printStackTrace()
            // Fetch from API failed, try to load from database
            val data = loadFromDB()
            emit(ResultOf.Success(data))
        }
    }

    private suspend fun loadFromDB(): List<Album> {
        return albumDao.getAlbums().map { album ->
            Album(id = album.id, title = album.title, userId = album.userId)
        }
    }

    private fun List<AlbumResponse>.toListEntity(): List<AlbumEntity> {
        return map { album ->
            AlbumEntity(
                id = album.id,
                title = album.title,
                userId = album.userId
            )
        }
    }
}
