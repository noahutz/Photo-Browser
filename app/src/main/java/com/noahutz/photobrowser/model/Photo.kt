package com.noahutz.photobrowser.model

data class Photo(
    val id: Int,
    val albumId: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)
