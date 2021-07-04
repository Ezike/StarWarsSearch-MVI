package com.ezike.tobenna.starwarssearch.character_detail.ui.views.planet

import com.ezike.tobenna.starwarssearch.character_detail.R
import com.ezike.tobenna.starwarssearch.character_detail.model.PlanetModel
import com.ezike.tobenna.starwarssearch.core.AppString
import com.ezike.tobenna.starwarssearch.core.ParamString
import com.ezike.tobenna.starwarssearch.core.StringResource

inline fun PlanetViewState.state(
    transform: PlanetViewStateFactory.() -> PlanetViewState
): PlanetViewState = transform(PlanetViewStateFactory(this))

object PlanetViewStateFactory {

    private lateinit var state: PlanetViewState

    operator fun invoke(
        viewState: PlanetViewState
    ): PlanetViewStateFactory {
        state = viewState
        return this
    }

    private val emptyPlanetData: PlanetViewData
        get() = PlanetViewData(
            name = StringResource(
                res = R.string.empty
            ),
            population = StringResource(
                res = R.string.empty
            )
        )

    val initialState: PlanetViewState
        get() = PlanetViewState(
            data = emptyPlanetData
        )

    val Loading: PlanetViewState
        get() = state.copy(
            data = emptyPlanetData,
            errorMessage = null,
            isLoading = true,
            showError = false,
            showPlanet = false,
            showTitle = true
        )

    val Hide: PlanetViewState
        get() = PlanetViewState(
            data = emptyPlanetData
        )

    fun Error(message: String): PlanetViewState =
        state.copy(
            data = emptyPlanetData,
            errorMessage = message,
            isLoading = false,
            showError = true,
            showPlanet = false,
            showTitle = true
        )

    fun Success(planet: PlanetModel): PlanetViewState =
        state.copy(
            data = PlanetViewData(
                ParamString(R.string.planet_name, planet.name),
                population = getPopulation(planet.population)
            ),
            errorMessage = null,
            isLoading = false,
            showError = false,
            showPlanet = true,
            showTitle = true
        )

    private fun getPopulation(
        population: String
    ): AppString = try {
        ParamString(R.string.population, population.toLong())
    } catch (e: Exception) {
        StringResource(R.string.population_not_available)
    }
}
