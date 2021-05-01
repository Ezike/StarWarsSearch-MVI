package com.ezike.tobenna.starwarssearch.character_search.views.detail

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.SpecieViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.model.SpecieModel
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.adapter.SpecieAdapter
import com.ezike.tobenna.starwarssearch.core.ext.init
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

data class RetryFetchSpecieIntent(val url: String) : ViewIntent

class SpecieView(
    private val binding: SpecieViewLayoutBinding,
    characterUrl: String
) : UIComponent<SpecieViewState>() {

    private val specieAdapter: SpecieAdapter by init { SpecieAdapter() }

    init {
        binding.specieList.adapter = specieAdapter
        binding.specieErrorState.onRetry { sendIntent(RetryFetchSpecieIntent(characterUrl)) }
    }

    override fun render(state: SpecieViewState) {
        specieAdapter.submitList(state.species)
        binding.run {
            specieTitle.isVisible = state.showTitle
            specieList.isVisible = state.showSpecies
            emptyView.isVisible = state.showEmpty
            specieLoadingView.root.isVisible = state.isLoading
            specieErrorState.isVisible = state.showError
            specieErrorState.setCaption(state.errorMessage)
        }
    }
}

data class SpecieViewState private constructor(
    val species: List<SpecieModel> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val showEmpty: Boolean = false,
    val showError: Boolean = false,
    val showTitle: Boolean = false,
    val showSpecies: Boolean = false
) : ViewState {

    inline fun state(transform: Factory.() -> SpecieViewState): SpecieViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: SpecieViewState

        operator fun invoke(viewState: SpecieViewState): Factory {
            state = viewState
            return this
        }

        val Init: SpecieViewState
            get() = SpecieViewState()

        val Loading: SpecieViewState
            get() = state.copy(
                isLoading = true,
                showEmpty = false,
                showError = false,
                showSpecies = false,
                showTitle = true,
                errorMessage = null
            )

        val Hide: SpecieViewState
            get() = SpecieViewState()

        fun Error(message: String): SpecieViewState =
            state.copy(
                isLoading = false,
                showEmpty = false,
                showError = true,
                showSpecies = false,
                showTitle = true,
                errorMessage = message
            )

        fun DataLoaded(species: List<SpecieModel>): SpecieViewState =
            state.copy(
                species = species,
                isLoading = false,
                showTitle = true,
                showEmpty = species.isEmpty(),
                showSpecies = species.isNotEmpty(),
                showError = false,
                errorMessage = null
            )
    }
}
