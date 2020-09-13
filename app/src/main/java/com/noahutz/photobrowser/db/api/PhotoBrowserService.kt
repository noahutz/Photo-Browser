package com.noahutz.photobrowser.db.api

import retrofit2.http.GET
import retrofit2.http.Path

interface PhotoBrowserService {
    @GET("albums/")
    suspend fun getAlbums(): List<AlbumResponse>

    @GET("albums/{albumId}/photos")
    suspend fun getPhotos(@Path("albumId") albumId: Int): List<PhotoResponse>
}
