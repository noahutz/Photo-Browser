package com.noahutz.photobrowser.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.`should be greater than`
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be`
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
        val result = runBlocking { apiService.getAlbumList() }

        result.size `should be greater than` 0
        result.forEach { item ->
            item.id `should not be` null
            item.title `should not be` null
            item.userId `should not be` null
        }
    }

    @Test
    fun `get photo list for existing id should return list size greater than 0`() {
        val resultAlbumList = runBlocking { apiService.getAlbumList() }
        val resultPhotoList =
            runBlocking { apiService.getPhotoList(resultAlbumList.first().id) }

        resultPhotoList.size `should be greater than` 0
        resultPhotoList.forEach { item ->
            item.albumId `should not be` null
            item.id `should not be` null
            item.thumbnailUrl `should not be` null
            item.title `should not be` null
            item.url `should not be` null
        }
    }

    @Test
    fun `get photo list for non-existing id should return list size 0`() {
        val resultPhotoList = runBlocking { apiService.getPhotoList(99999) }

        resultPhotoList.size `should be` 0
    }
}