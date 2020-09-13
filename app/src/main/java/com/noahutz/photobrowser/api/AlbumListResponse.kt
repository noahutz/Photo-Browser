package com.noahutz.photobrowser.api

class AlbumListResponse : ArrayList<AlbumItem>()

data class AlbumItem(val id: Int, val title: String, val userId: Int)
