package pl.birski.uiherodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.birski.core.DataState
import pl.birski.herointeractors.GetHeroFromCacheUseCase
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel
@Inject constructor(
    private val getHeroFromCacheUseCase: GetHeroFromCacheUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())

    init {
        savedStateHandle.get<Int>("heroId")?.let {
            onTriggerEvent(HeroDetailEvents.GetHeroFromCache(it))
        }
    }

    fun onTriggerEvent(event: HeroDetailEvents) {
        when (event) {
            is HeroDetailEvents.GetHeroFromCache -> getHeroFromCache(event.id)
        }
    }

    private fun getHeroFromCache(id: Int) {
        getHeroFromCacheUseCase.execute(id).onEach {
            when (it) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = it.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(hero = it.data)
                }
                is DataState.Response -> {
                    TODO("Handle error")
                }
            }
        }.launchIn(viewModelScope)
    }
}
