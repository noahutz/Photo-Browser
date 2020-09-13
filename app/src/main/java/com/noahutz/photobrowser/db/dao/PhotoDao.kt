package com.noahutz.photobrowser.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noahutz.photobrowser.db.entity.PhotoEntity

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo WHERE album_id = :albumId")
    suspend fun getPhotos(albumId: Int): List<PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoEntity)
}
