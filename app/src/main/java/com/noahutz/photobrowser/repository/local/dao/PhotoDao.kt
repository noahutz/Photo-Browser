package com.noahutz.photobrowser.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.noahutz.photobrowser.repository.local.entity.Photo

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo")
    fun findAll(): List<Photo>

    @Query("SELECT * FROM photo WHERE id = :id")
    fun findBy(id: Int): Photo

    @Insert
    fun insertAll(vararg photo: Photo)
}
