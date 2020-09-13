package com.noahutz.photobrowser.di

import com.noahutz.photobrowser.util.ImageLoader
import com.noahutz.photobrowser.util.PicassoImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun provideImageLoader(): ImageLoader {
        return PicassoImageLoader()
    }
}
