package pl.birski.herointeractors

import pl.birski.herodatasource.network.HeroService

data class UseCaseFactory(
    val getHeroesUseCase: GetHeroesUseCase
) {
    companion object Factory {
        fun build(): UseCaseFactory{
            val service = HeroService.build()
            return UseCaseFactory(
                getHeroesUseCase = GetHeroesUseCase(
                    service = service
                )
            )
        }
    }
}