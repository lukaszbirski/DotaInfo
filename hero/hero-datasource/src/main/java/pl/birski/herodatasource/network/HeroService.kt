package pl.birski.herodatasource.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.url
import pl.birski.herodomain.Hero

interface HeroService {

    suspend fun getHeroStats(): List<Hero>

    companion object Factory {
        fun build() = HeroServiceImpl(
            httpClient = HttpClient(Android) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(
                        kotlinx.serialization.json.Json {
                            ignoreUnknownKeys = true // if the server sends extra fields, ignore them
                        }
                    )
                }
            }
        )
    }
}

class HeroServiceImpl(
    private val httpClient: HttpClient
) : HeroService {

    override suspend fun getHeroStats(): List<Hero> {
        return httpClient.get<List<HeroDto>> { url(EndPoints.HERO_STATS) }.map { it.toHero() }
    }
}
