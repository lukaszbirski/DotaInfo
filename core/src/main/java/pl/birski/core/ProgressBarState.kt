package pl.birski.core

sealed class ProgressBarState {

    object Loading : ProgressBarState()

    object Idle : ProgressBarState()
}
