package com.noahutz.photobrowser.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noahutz.photobrowser.db.dao.AlbumDao
import com.noahutz.photobrowser.db.dao.PhotoDao
import com.noahutz.photobrowser.db.entity.AlbumEntity
import com.noahutz.photobrowser.db.entity.PhotoEntity

@Database(entities = [AlbumEntity::class, PhotoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
}
