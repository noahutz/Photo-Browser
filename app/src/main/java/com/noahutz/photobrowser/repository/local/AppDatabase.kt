package com.noahutz.photobrowser.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noahutz.photobrowser.repository.local.dao.AlbumDao
import com.noahutz.photobrowser.repository.local.dao.PhotoDao
import com.noahutz.photobrowser.repository.local.entity.Album
import com.noahutz.photobrowser.repository.local.entity.Photo

@Database(entities = [Album::class, Photo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
}
