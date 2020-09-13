package com.noahutz.photobrowser.repository.remote

class AlbumListResponse : ArrayList<AlbumItem>()

data class AlbumItem(val id: Int, val title: String, val userId: Int)
