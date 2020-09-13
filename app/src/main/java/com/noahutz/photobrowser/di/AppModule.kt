package com.noahutz.photobrowser.di

import com.noahutz.photobrowser.api.PhotoBrowserService
import com.noahutz.photobrowser.db.dao.AlbumDao
import com.noahutz.photobrowser.repository.AlbumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @Provides
    fun provideApiService(): PhotoBrowserService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotoBrowserService::class.java)
    }

    @Provides
    fun provideAlbumRepository(
        apiService: PhotoBrowserService,
        albumDao: AlbumDao
    ): AlbumRepository {
        return AlbumRepository(apiService = apiService, albumDao = albumDao)
    }
}