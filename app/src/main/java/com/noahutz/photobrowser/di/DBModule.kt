package com.noahutz.photobrowser.di

import android.content.Context
import androidx.room.Room
import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.db.AppDatabase
import com.noahutz.photobrowser.db.dao.AlbumDao
import com.noahutz.photobrowser.db.dao.PhotoDao
import com.noahutz.photobrowser.repository.AlbumRepository
import com.noahutz.photobrowser.repository.PhotoRepository
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

    @Provides
    fun providePhotoDao(appDatabase: AppDatabase): PhotoDao {
        return appDatabase.photoDao()
    }

    @Provides
    fun provideAlbumRepository(
        apiService: PhotoBrowserService,
        albumDao: AlbumDao
    ): AlbumRepository {
        return AlbumRepository(apiService = apiService, albumDao = albumDao)
    }

    @Provides
    fun providePhotoRepository(
        apiService: PhotoBrowserService,
        photoDao: PhotoDao
    ): PhotoRepository {
        return PhotoRepository(apiService = apiService, photoDao = photoDao)
    }
}