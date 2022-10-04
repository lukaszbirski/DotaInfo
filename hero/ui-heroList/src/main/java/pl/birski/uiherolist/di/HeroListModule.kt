package pl.birski.uiherolist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.birski.core.Logger
import pl.birski.herointeractors.UseCaseFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    @Named("HeroListLogger")
    fun provideHeroListLogger() = Logger(tag = "HeroListLogger", isDebug = true)

    @Provides
    @Singleton
    fun provideGetHeroesUseCase(
        useCaseFactory: UseCaseFactory
    ) = useCaseFactory.getHeroesUseCase
}
