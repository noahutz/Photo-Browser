package com.noahutz.photobrowser.repository.remote

class PhotoListResponse : ArrayList<PhotoItem>()

data class PhotoItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)
