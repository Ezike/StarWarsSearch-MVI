package com.ezike.tobenna.starwarssearch.character_detail.ui.views.planet

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_detail.R
import com.ezike.tobenna.starwarssearch.character_detail.databinding.PlanetViewLayoutBinding
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class PlanetView(
    private val view: PlanetViewLayoutBinding,
    characterUrl: String
) : UIComponent<PlanetViewState>() {

    init {
        view.planetErrorState.onRetry {
            sendIntent(RetryFetchPlanetIntent(characterUrl))
        }
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

    private fun getPopulation(population: String): String = try {
        view.root.context.getString(
            R.string.population,
            population.toLong()
        )
    } catch (e: Exception) {
        view.root.context.getString(R.string.population_not_available)
    }
}
