package com.noahutz.photobrowser.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noahutz.photobrowser.db.entity.AlbumEntity

@Dao
interface AlbumDao {
    @Query("SELECT * FROM album")
    suspend fun getAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM album WHERE id = :id")
    suspend fun getAlbum(id: Int): AlbumEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)
}
