package pl.birski.uiherodetail.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.birski.herointeractors.GetHeroFromCacheUseCase
import pl.birski.herointeractors.UseCaseFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailModule {

    @Provides
    @Singleton
    fun provideGetHeroFromCacheUseCase(
        useCaseFactory: UseCaseFactory
    ): GetHeroFromCacheUseCase {
        return useCaseFactory.getHeroFromCacheUseCase
    }
}
