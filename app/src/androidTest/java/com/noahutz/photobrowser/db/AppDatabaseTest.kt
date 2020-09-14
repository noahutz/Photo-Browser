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
    companion object {
        private const val ALBUM_ID = 1

        private val ALBUMS = (1..10).map { id ->
            AlbumEntity(id = id, title = "Title $id", userId = 1)
        }
        private val NEW_ALBUMS = ALBUMS.map { it.copy(title = "New Title ${it.id}") }

        private val PHOTOS = (1..10).map { id ->
            PhotoEntity(
                id = id,
                albumId = ALBUM_ID,
                thumbnailUrl = "some url",
                title = "some title",
                url = "some url"
            )
        }
        private val NEW_PHOTOS = PHOTOS.map { it.copy(title = "New Title ${it.id}") }
    }

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
    fun saveAndLoadAlbums() = runBlocking {
        albumDao.insertAlbums(ALBUMS)

        val result = albumDao.getAlbums()

        assertEquals(result, ALBUMS)
    }

    @Test
    fun overwriteAndLoadAlbums() = runBlocking {
        albumDao.insertAlbums(ALBUMS)
        albumDao.insertAlbums(NEW_ALBUMS)

        val result = albumDao.getAlbums()

        assertEquals(result, NEW_ALBUMS)
    }


    @Test
    fun loadNonExistingAlbum() = runBlocking {
        val result = albumDao.getAlbum(5)

        assertNull(result)
    }

    @Test
    fun saveAndLoadPhotos() = runBlocking {
        photoDao.insertPhotos(PHOTOS)

        val result = photoDao.getPhotos(ALBUM_ID)

        assertEquals(result, PHOTOS)
    }

    @Test
    fun overwriteAndLoadPhotos() = runBlocking {
        photoDao.insertPhotos(PHOTOS)
        photoDao.insertPhotos(NEW_PHOTOS)

        val result = photoDao.getPhotos(ALBUM_ID)

        assertEquals(result, NEW_PHOTOS)
    }

    @Test
    fun loadNonExistingPhoto() = runBlocking {
        val result = photoDao.getPhotos(5)

        assertEquals(result.size, 0)
    }
}
