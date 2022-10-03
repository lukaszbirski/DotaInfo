package pl.birski.uiherolist

import pl.birski.core.ProgressBarState
import pl.birski.herodomain.Hero

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heroes: List<Hero> = listOf()
)
