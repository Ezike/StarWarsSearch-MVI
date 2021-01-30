package com.ezike.tobenna.starwarssearch.character_search.views.detail

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.PlanetViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.model.PlanetModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

data class RetryFetchPlanetIntent(val url: String) : ViewIntent

class PlanetView(
    private val binding: PlanetViewLayoutBinding,
    characterUrl: String
) : UIComponent<PlanetViewState>() {

    init {
        binding.planetErrorState.onRetry { sendIntent(RetryFetchPlanetIntent(characterUrl)) }
    }

    private fun getPopulation(population: String): String = try {
        binding.root.context.getString(
            R.string.population,
            population.toLong()
        )
    } catch (e: Exception) {
        binding.root.context.getString(R.string.population_not_available)
    }

    override fun render(state: PlanetViewState) {
        binding.run {
            root.isVisible = state.isVisible
            if (state.isVisible) {
                planetCardView.isVisible = !state.isLoading && state.errorMessage == null
                planetLoadingView.root.isVisible = state.isLoading
                planetErrorState.isVisible = state.errorMessage != null
                planetErrorState.setCaption(state.errorMessage)
                if (state.planet != null) {
                    planetName.text =
                        root.context.getString(R.string.planet_name, state.planet.name)
                    planetPopulation.text = getPopulation(state.planet.population)
                }
            }
        }
    }
}

data class PlanetViewState private constructor(
    val planet: PlanetModel? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = true,
    val isVisible: Boolean = true
) : ViewState {

    inline fun state(transform: Factory.() -> PlanetViewState): PlanetViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: PlanetViewState

        operator fun invoke(viewState: PlanetViewState): Factory {
            state = viewState
            return this
        }

        val init: PlanetViewState
            get() = PlanetViewState()

        val loading: PlanetViewState
            get() = state.copy(isLoading = true, isVisible = true, errorMessage = null)

        val hide: PlanetViewState
            get() = state.copy(isLoading = false, isVisible = false, errorMessage = null)

        fun error(message: String): PlanetViewState =
            state.copy(isLoading = false, isVisible = true, errorMessage = message)

        fun success(planet: PlanetModel): PlanetViewState =
            state.copy(
                planet = planet,
                isLoading = false,
                isVisible = true,
                errorMessage = null
            )
    }
}
