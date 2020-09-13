package com.noahutz.photobrowser.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.noahutz.photobrowser.db.entity.PhotoEntity

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo")
    fun findAll(): List<PhotoEntity>

    @Query("SELECT * FROM photo WHERE id = :id")
    fun findBy(id: Int): PhotoEntity

    @Insert
    fun insertAll(vararg photoEntity: PhotoEntity)
}
