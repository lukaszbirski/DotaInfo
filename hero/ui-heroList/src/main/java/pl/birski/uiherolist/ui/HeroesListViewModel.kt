package pl.birski.uiherolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.birski.core.DataState
import pl.birski.core.Logger
import pl.birski.core.UIComponent
import pl.birski.herointeractors.GetHeroesUseCase
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroesListViewModel @Inject constructor(
    private val getHeroes: GetHeroesUseCase,
    @Named("HeroListLogger") private val logger: Logger
) : ViewModel() {

    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())

    init {
        onTriggerEvent(HeroListEvents.GetHeroes)
    }

    fun onTriggerEvent(event: HeroListEvents) = when (event) {
        is HeroListEvents.GetHeroes -> getHeroes()
    }

    private fun getHeroes() {
        getHeroes.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(heroes = dataState.data ?: listOf())
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }
}
