package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.planet

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character.detail.databinding.PlanetViewLayoutBinding
import com.ezike.tobenna.starwarssearch.core.string
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class PlanetView(
    private val view: PlanetViewLayoutBinding,
    characterUrl: String
) : UIComponent<PlanetViewState>() {

    init {
        view.planetError.onRetry {
            sendIntent(RetryFetchPlanetIntent(characterUrl))
        }
    }

    override fun render(state: PlanetViewState) {
        view.run {
            planetView.isVisible = state.showPlanet
            planetTitle.isVisible = state.showTitle
            planetName.string = state.data.name
            planetPopulation.string = state.data.population
            planetLoadingView.root.isVisible = state.isLoading
            planetError.isVisible = state.showError
            planetError.setCaption(state.errorMessage)
        }
    }
}
