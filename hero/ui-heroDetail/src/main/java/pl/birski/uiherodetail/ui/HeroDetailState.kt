package pl.birski.uiherodetail.ui

import pl.birski.core.ProgressBarState
import pl.birski.herodomain.Hero

data class HeroDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null
)
