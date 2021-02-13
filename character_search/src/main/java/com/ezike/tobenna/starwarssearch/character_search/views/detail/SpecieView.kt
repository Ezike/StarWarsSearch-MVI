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
            root.isVisible = state.isVisible
            if (state.isVisible) {
                emptyView.isVisible =
                    state.species.isEmpty() && !state.isLoading && state.errorMessage == null
                specieLoadingView.root.isVisible = state.isLoading
                specieErrorState.isVisible = state.errorMessage != null
                specieErrorState.setCaption(state.errorMessage)
            }
        }
    }
}

data class SpecieViewState private constructor(
    val species: List<SpecieModel> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = true,
    val isVisible: Boolean = true
) : ViewState {

    inline fun state(transform: Factory.() -> SpecieViewState): SpecieViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: SpecieViewState

        operator fun invoke(viewState: SpecieViewState): Factory {
            state = viewState
            return this
        }

        val init: SpecieViewState
            get() = SpecieViewState()

        val loading: SpecieViewState
            get() = state.copy(isLoading = true, isVisible = true, errorMessage = null)

        val hide: SpecieViewState
            get() = state.copy(isLoading = false, isVisible = false, errorMessage = null)

        fun error(message: String): SpecieViewState =
            state.copy(isLoading = false, isVisible = true, errorMessage = message)

        fun success(species: List<SpecieModel>): SpecieViewState =
            state.copy(
                species = species,
                isLoading = false,
                isVisible = true,
                errorMessage = null
            )
    }
}
