package com.noahutz.photobrowser.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.noahutz.photobrowser.db.entity.AlbumEntity

@Dao
interface AlbumDao {
    @Query("SELECT * FROM album")
    fun findAll(): List<AlbumEntity>

    @Query("SELECT * FROM album WHERE id = :id")
    fun findBy(id: Int): AlbumEntity

    @Insert
    fun insertAll(vararg albumEntity: AlbumEntity)
}
