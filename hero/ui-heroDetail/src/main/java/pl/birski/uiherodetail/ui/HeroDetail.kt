package pl.birski.uiherodetail.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HeroDetail(
    state: HeroDetailState
) {
    state.hero?.let {
        Text(text = "Hero id: ${it.localizedName}")
    } ?: Text(text = "LOADING...")
}
