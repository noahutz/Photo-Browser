package com.noahutz.photobrowser.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeGreaterThan
import org.amshove.kluent.shouldNotBe
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotoBrowserServiceTest {

    private val mockWebServer = MockWebServer()
    private lateinit var apiService: PhotoBrowserService

    @Before
    fun setup() {
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotoBrowserService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get album list should return list greater than 0`() {
        val result = runBlocking { apiService.getAlbums() }

        val albums = result.body()!!
        albums.size shouldBeGreaterThan 0
        albums.forEach { item ->
            item.id shouldNotBe null
            item.title shouldNotBe null
            item.userId shouldNotBe null
        }
    }

    @Test
    fun `get photo list for existing id should return list size greater than 0`() {
        val resultAlbums = runBlocking { apiService.getAlbums() }
        val resultPhotos = runBlocking { apiService.getPhotos(resultAlbums.body()!![0].id) }

        val photos = resultPhotos.body()!!
        photos.size shouldBeGreaterThan 0
        photos.forEach { photo ->
            photo.albumId shouldNotBe null
            photo.id shouldNotBe null
            photo.thumbnailUrl shouldNotBe null
            photo.title shouldNotBe null
            photo.url shouldNotBe null
        }
    }

    @Test
    fun `get photo list for non-existing id should return list size 0`() {
        val resultPhotos = runBlocking { apiService.getPhotos(99999) }

        val photos = resultPhotos.body()!!
        photos.size shouldBe 0
    }
}