package pl.birski.herointeractors

import com.squareup.sqldelight.db.SqlDriver
import pl.birski.herodatasource.cache.HeroCache
import pl.birski.herodatasource.network.HeroService

data class UseCaseFactory(
    val getHeroesUseCase: GetHeroesUseCase,
    val getHeroFromCacheUseCase: GetHeroFromCacheUseCase
) {
    companion object Factory {
        fun build(sqlDriver: SqlDriver): UseCaseFactory {
            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)
            return UseCaseFactory(
                getHeroesUseCase = GetHeroesUseCase(
                    service = service,
                    cache = cache
                ),
                getHeroFromCacheUseCase = GetHeroFromCacheUseCase(
                    cache = cache
                )
            )
        }

        val schema: SqlDriver.Schema = HeroCache.schema

        val dbName: String = HeroCache.dbName
    }
}
