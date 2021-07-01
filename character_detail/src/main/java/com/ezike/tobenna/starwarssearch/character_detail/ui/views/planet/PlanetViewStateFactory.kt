package com.ezike.tobenna.starwarssearch.character_detail.ui.views.planet

import com.ezike.tobenna.starwarssearch.character_detail.model.PlanetModel

inline fun PlanetViewState.state(
    transform: PlanetViewStateFactory.() -> PlanetViewState
): PlanetViewState = transform(PlanetViewStateFactory(this))

object PlanetViewStateFactory {

    private lateinit var state: PlanetViewState

    operator fun invoke(viewState: PlanetViewState): PlanetViewStateFactory {
        state = viewState
        return this
    }

    val initialState: PlanetViewState
        get() = PlanetViewState()

    val Loading: PlanetViewState
        get() = state.copy(
            planet = null,
            errorMessage = null,
            isLoading = true,
            showError = false,
            showPlanet = false,
            showTitle = true
        )

    val Hide: PlanetViewState
        get() = PlanetViewState()

    fun Error(message: String): PlanetViewState =
        state.copy(
            planet = null,
            errorMessage = message,
            isLoading = false,
            showError = true,
            showPlanet = false,
            showTitle = true
        )

    fun Success(planet: PlanetModel): PlanetViewState =
        state.copy(
            planet = planet,
            errorMessage = null,
            isLoading = false,
            showError = false,
            showPlanet = true,
            showTitle = true
        )
}
