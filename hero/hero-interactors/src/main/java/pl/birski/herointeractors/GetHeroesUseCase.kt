package pl.birski.herointeractors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.birski.core.DataState
import pl.birski.core.ProgressBarState
import pl.birski.core.UIComponent
import pl.birski.herodatasource.cache.HeroCache
import pl.birski.herodatasource.network.HeroService
import pl.birski.herodomain.Hero

class GetHeroesUseCase(
    private val cache: HeroCache,
    private val service: HeroService
) {

    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val heroes = try {
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(emitErrorDataState("Network error", e))
                listOf()
            }

            cache.insert(heroes)

            val cachedHeroes = cache.selectAll()

            emit(DataState.Data(cachedHeroes))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emitErrorDataState("title", e))
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

    private fun emitErrorDataState(title: String, e: Exception) = DataState.Response<List<Hero>>(
        uiComponent = UIComponent.Dialog(
            title = title,
            description = e.message ?: "Unknown Error"
        )
    )
}
