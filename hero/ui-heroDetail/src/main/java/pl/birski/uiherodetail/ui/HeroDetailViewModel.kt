package pl.birski.uiherodetail.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.birski.herointeractors.GetHeroFromCacheUseCase
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel
@Inject constructor(
    private val getHeroFromCacheUseCase: GetHeroFromCacheUseCase
) : ViewModel()
