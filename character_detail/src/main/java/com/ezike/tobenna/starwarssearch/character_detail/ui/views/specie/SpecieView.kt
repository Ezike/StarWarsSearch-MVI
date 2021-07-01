package com.ezike.tobenna.starwarssearch.character_detail.ui.views.specie

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_detail.databinding.SpecieViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_detail.ui.adapter.SpecieAdapter
import com.ezike.tobenna.starwarssearch.core.ext.init
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class SpecieView(
    private val view: SpecieViewLayoutBinding,
    characterUrl: String
) : UIComponent<SpecieViewState>() {

    private val specieAdapter: SpecieAdapter by init { SpecieAdapter() }

    init {
        view.specieList.adapter = specieAdapter
        view.specieErrorState.onRetry {
            sendIntent(RetryFetchSpecieIntent(characterUrl))
        }
    }

    override fun render(state: SpecieViewState) {
        specieAdapter.submitList(state.species)
        view.run {
            specieTitle.isVisible = state.showTitle
            specieList.isVisible = state.showSpecies
            emptyView.isVisible = state.showEmpty
            specieLoadingView.root.isVisible = state.isLoading
            specieErrorState.isVisible = state.showError
            specieErrorState.setCaption(state.errorMessage)
        }
    }
}
