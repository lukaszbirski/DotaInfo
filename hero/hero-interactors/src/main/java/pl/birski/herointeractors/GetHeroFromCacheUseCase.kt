package pl.birski.herointeractors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.birski.core.DataState
import pl.birski.core.ProgressBarState
import pl.birski.core.UIComponent
import pl.birski.herodatasource.cache.HeroCache
import pl.birski.herodomain.Hero

class GetHeroFromCacheUseCase(
    private val cache: HeroCache
) {

    fun execute(id: Int): Flow<DataState<Hero>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val hero = cache.getHero(id) ?: throw Exception("Hero does not exist in the cache.")
            emit(DataState.Data(hero))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                emitErrorDataState("Error", e)
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

    private fun emitErrorDataState(title: String, e: Exception) = DataState.Response<Hero>(
        uiComponent = UIComponent.Dialog(
            title = title,
            description = e.message ?: "Unknown Error"
        )
    )
}
