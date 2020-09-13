package com.noahutz.photobrowser.repository.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noahutz.photobrowser.repository.local.dao.AlbumDao
import com.noahutz.photobrowser.repository.local.dao.PhotoDao
import com.noahutz.photobrowser.repository.local.entity.Album
import com.noahutz.photobrowser.repository.local.entity.Photo
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
    fun saveAndLoadAlbum() {
        val album = Album(id = 1, title = "Title", userId = 1)
        albumDao.insertAll(album)

        val result = albumDao.findBy(1)

        assertEquals(result, album)
    }

    @Test
    fun saveAndLoadAllAlbums() {
        val albums = (1..10).map { id -> Album(id = id, title = "Title $id", userId = 1) }
        albumDao.insertAll(*albums.toTypedArray())

        val result = albumDao.findAll()

        assertEquals(result, albums)
    }

    @Test
    fun loadNonExistingAlbum() {
        val result = albumDao.findBy(5)

        assertNull(result)
    }


    @Test
    fun saveAndLoadPhoto() {
        val album = Album(id = 1, title = "Title", userId = 1)
        albumDao.insertAll(album)

        val result = albumDao.findBy(1)

        assertEquals(result, album)
    }

    @Test
    fun saveAndLoadAllPhotos() {
        val photos = (1..10).map { id ->
            Photo(
                id = id,
                albumId = 1,
                thumbnailUrl = "some url",
                title = "some title",
                url = "some url"
            )
        }
        photoDao.insertAll(*photos.toTypedArray())

        val result = photoDao.findAll()

        assertEquals(result, photos)
    }

    @Test
    fun loadNonExistingPhoto() {
        val result = photoDao.findBy(5)

        assertNull(result)
    }
}
