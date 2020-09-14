package com.noahutz.photobrowser.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotoBrowserService {
    @GET("albums/")
    suspend fun getAlbums(): Response<List<AlbumResponse>>

    @GET("albums/{albumId}/photos")
    suspend fun getPhotos(@Path("albumId") albumId: Int): Response<List<PhotoResponse>>
}
