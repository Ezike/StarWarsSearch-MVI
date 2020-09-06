package com.ezike.tobenna.starwarssearch.character_search.views.detail

import androidx.annotation.UiThread
import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.PlanetViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.model.PlanetModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.UIComponent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

data class PlanetViewState(
    val planet: PlanetModel? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = true,
    val isVisible: Boolean = true
) : ViewState {

    val loading: PlanetViewState
        get() = copy(isLoading = true, isVisible = true, errorMessage = null)

    val hide: PlanetViewState
        get() = copy(isLoading = false, isVisible = false, errorMessage = null)

    fun error(message: String): PlanetViewState =
        copy(isLoading = false, isVisible = true, errorMessage = message)

    fun success(planet: PlanetModel): PlanetViewState =
        copy(planet = planet, isLoading = false, isVisible = true, errorMessage = null)
}

data class RetryFetchPlanetIntent(val url: String) : ViewIntent

class PlanetView(
    private val binding: PlanetViewLayoutBinding,
    private val characterUrl: String,
    action: DispatchIntent
) : UIComponent<PlanetViewState>() {

    init {
        binding.planetErrorState.onRetry { action(RetryFetchPlanetIntent(characterUrl)) }
    }

    @UiThread
    override fun render(state: PlanetViewState) {
        binding.run {
            root.isVisible = state.isVisible
            if (state.isVisible) {
                planetCardView.isVisible = !state.isLoading && state.errorMessage == null
                planetLoadingView.root.isVisible = state.isLoading
                planetErrorState.isVisible = state.errorMessage != null
                if (state.planet != null) {
                    planetName.text =
                        root.context.getString(R.string.planet_name, state.planet.name)
                    planetPopulation.text = getPopulation(state.planet.population)
                }
                planetErrorState.setCaption(state.errorMessage)
            }
        }
    }

    private fun getPopulation(population: String): String {
        return try {
            binding.root.context.getString(
                R.string.population,
                population.toLong()
            )
        } catch (e: Exception) {
            binding.root.context.getString(R.string.population_not_available)
        }
    }
}
