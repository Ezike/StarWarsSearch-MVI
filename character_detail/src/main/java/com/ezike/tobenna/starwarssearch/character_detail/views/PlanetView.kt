package com.ezike.tobenna.starwarssearch.character_detail.views

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_detail.R
import com.ezike.tobenna.starwarssearch.character_detail.databinding.PlanetViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.model.PlanetModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

data class RetryFetchPlanetIntent(val url: String) : ViewIntent

class PlanetView(
    private val view: PlanetViewLayoutBinding,
    characterUrl: String
) : UIComponent<PlanetViewState>() {

    init {
        view.planetErrorState.onRetry { sendIntent(RetryFetchPlanetIntent(characterUrl)) }
    }

    private fun getPopulation(population: String): String = try {
        view.root.context.getString(
            R.string.population,
            population.toLong()
        )
    } catch (e: Exception) {
        view.root.context.getString(R.string.population_not_available)
    }

    override fun render(state: PlanetViewState) {
        view.run {
            planetView.isVisible = state.showPlanet
            planetTitle.isVisible = state.showTitle
            if (state.planet != null) {
                planetName.text =
                    root.context.getString(R.string.planet_name, state.planet.name)
                planetPopulation.text = getPopulation(state.planet.population)
            }
            planetLoadingView.root.isVisible = state.isLoading
            planetErrorState.isVisible = state.showError
            planetErrorState.setCaption(state.errorMessage)
        }
    }
}

data class PlanetViewState private constructor(
    val planet: PlanetModel? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val showPlanet: Boolean = false,
    val showTitle: Boolean = false
) : ViewState {

    inline fun state(transform: Factory.() -> PlanetViewState): PlanetViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: PlanetViewState

        operator fun invoke(viewState: PlanetViewState): Factory {
            state = viewState
            return this
        }

        val Init: PlanetViewState
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
}
