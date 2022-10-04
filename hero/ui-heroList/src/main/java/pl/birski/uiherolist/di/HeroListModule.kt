package pl.birski.uiherolist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.birski.herointeractors.UseCaseFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    fun provideGetHeroesUseCase(
        useCaseFactory: UseCaseFactory
    ) = useCaseFactory.getHeroesUseCase
}
