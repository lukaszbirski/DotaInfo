package pl.birski.dotainfo.di

import android.app.Application
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.birski.dotainfo.R
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {

    @Provides
    @Singleton
    fun provideImageLoader(app: Application) = ImageLoader.Builder(app)
        .error(R.drawable.error_image)
        .placeholder(R.drawable.white_background)
        .availableMemoryPercentage(0.25)
        .crossfade(true)
        .build()
}
