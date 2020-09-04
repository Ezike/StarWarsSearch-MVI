package com.ezike.tobenna.starwarssearch.character_search.views.detail

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.SpecieViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.model.SpecieModel
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.adapter.SpecieAdapter
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

data class SpecieViewState(
    val species: List<SpecieModel> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = true,
    val isVisible: Boolean = true
) : ViewState {

    val loading: SpecieViewState
        get() = copy(isLoading = true, isVisible = true, errorMessage = null)

    val hide: SpecieViewState
        get() = copy(isLoading = false, isVisible = false, errorMessage = null)

    fun error(message: String): SpecieViewState =
        copy(isLoading = false, isVisible = true, errorMessage = message)

    fun success(species: List<SpecieModel>): SpecieViewState =
        copy(species = species, isLoading = false, isVisible = true, errorMessage = null)
}

data class RetryFetchSpecieIntent(val url: String) : ViewIntent

class SpecieView(
    private val binding: SpecieViewLayoutBinding,
    private val characterUrl: String,
    action: DispatchIntent
) {

    private val specieAdapter: SpecieAdapter by lazy(LazyThreadSafetyMode.NONE) { SpecieAdapter() }

    init {
        binding.specieList.adapter = specieAdapter
        binding.specieErrorState.onRetry { action(RetryFetchSpecieIntent(characterUrl)) }
    }

    fun render(state: SpecieViewState) {
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
