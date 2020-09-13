package com.noahutz.photobrowser.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noahutz.photobrowser.db.dao.AlbumDao
import com.noahutz.photobrowser.db.dao.PhotoDao
import com.noahutz.photobrowser.db.entity.AlbumEntity
import com.noahutz.photobrowser.db.entity.PhotoEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var albumDao: AlbumDao
    private lateinit var photoDao: PhotoDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        albumDao = db.albumDao()
        photoDao = db.photoDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveAndLoadAlbum() = runBlocking {
        val album = AlbumEntity(id = 1, title = "Title", userId = 1)
        albumDao.insertAlbum(album)

        val result = albumDao.getAlbum(1)

        assertEquals(result, album)
    }

    @Test
    fun saveAndLoadAllAlbums() = runBlocking {
        val albums = (1..10).map { id -> AlbumEntity(id = id, title = "Title $id", userId = 1) }
        albums.forEach { album ->
            albumDao.insertAlbum(album)
        }

        val result = albumDao.getAlbums()

        assertEquals(result, albums)
    }

    @Test
    fun loadNonExistingAlbum() = runBlocking {
        val result = albumDao.getAlbum(5)

        assertNull(result)
    }


    @Test
    fun saveAndLoadPhoto() = runBlocking {
        val album = AlbumEntity(id = 1, title = "Title", userId = 1)
        albumDao.insertAlbum(album)

        val result = albumDao.getAlbum(1)

        assertEquals(result, album)
    }

    @Test
    fun saveAndLoadAllPhotos() = runBlocking {
        val albumId = 1
        val photos = (1..10).map { id ->
            PhotoEntity(
                id = id,
                albumId = albumId,
                thumbnailUrl = "some url",
                title = "some title",
                url = "some url"
            )
        }
        photos.forEach { photo ->
            photoDao.insertPhoto(photo)
        }

        val result = photoDao.getPhotos(albumId)

        assertEquals(result, photos)
    }

    @Test
    fun loadNonExistingPhoto() = runBlocking {
        val result = photoDao.getPhotos(5)

        assertNull(result)
    }
}
