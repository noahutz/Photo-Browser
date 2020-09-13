package com.noahutz.photobrowser.repository.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface PhotoBrowserService {
    @GET("albums/")
    suspend fun getAlbumList(): AlbumListResponse

    @GET("albums/{albumId}/photos")
    suspend fun getPhotoList(@Path("albumId") albumId: Int): PhotoListResponse
}
