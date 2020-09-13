package com.noahutz.photobrowser.api

data class AlbumResponse(val id: Int, val title: String, val userId: Int)

data class PhotoResponse(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)
