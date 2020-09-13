package com.noahutz.photobrowser.di

import android.content.Context
import androidx.room.Room
import com.noahutz.photobrowser.db.AppDatabase
import com.noahutz.photobrowser.db.dao.AlbumDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DBModule {
    companion object {
        private const val DB_NAME = "PhotoDB"
    }

    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideAlbumDao(appDatabase: AppDatabase): AlbumDao {
        return appDatabase.albumDao()
    }
}