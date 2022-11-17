package pl.birski.uiherodetail.ui

sealed class HeroDetailEvents {

    data class GetHeroFromCache(
        val id: Int
    ) : HeroDetailEvents()
}
