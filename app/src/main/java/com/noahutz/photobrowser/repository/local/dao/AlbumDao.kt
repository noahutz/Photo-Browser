package com.noahutz.photobrowser.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.noahutz.photobrowser.repository.local.entity.Album

@Dao
interface AlbumDao {
    @Query("SELECT * FROM album")
    fun findAll(): List<Album>

    @Query("SELECT * FROM album WHERE id = :id")
    fun findBy(id: Int): Album

    @Insert
    fun insertAll(vararg album: Album)
}
