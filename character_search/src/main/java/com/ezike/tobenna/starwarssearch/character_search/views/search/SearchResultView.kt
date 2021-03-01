package com.ezike.tobenna.starwarssearch.character_search.views.search

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchResultBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.RetrySearchIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SaveSearchIntent
import com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter.SearchResultAdapter
import com.ezike.tobenna.starwarssearch.core.ext.init
import com.ezike.tobenna.starwarssearch.core.ext.show
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class SearchResultView(
    private val binding: LayoutSearchResultBinding,
    query: () -> String,
    navigationAction: (CharacterModel) -> Unit
) : UIComponent<SearchResultViewState>() {

    private val searchResultAdapter: SearchResultAdapter by init {
        SearchResultAdapter { model ->
            sendIntent(SaveSearchIntent(model))
            navigationAction(model)
        }
    }

    init {
        binding.charactersRv.adapter = searchResultAdapter
        binding.errorState.onRetry { sendIntent(RetrySearchIntent(query())) }
    }

    override fun render(state: SearchResultViewState) {
        searchResultAdapter.submitList(state.searchResult)
        binding.run {
            charactersRv.show = state.showResult
            progressBar.isVisible = state.showProgress
            emptyState.isVisible = state.showEmpty
            errorState.isVisible = state.showError
            errorState.setCaption(state.error)
        }
    }
}

data class SearchResultViewState private constructor(
    val searchResult: List<CharacterModel> = emptyList(),
    val showProgress: Boolean = false,
    val showEmpty: Boolean = false,
    val showResult: Boolean = false,
    val showError: Boolean = false,
    val error: String? = null
) : ViewState {

    inline fun state(transform: Factory.() -> SearchResultViewState): SearchResultViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: SearchResultViewState

        operator fun invoke(viewState: SearchResultViewState): Factory {
            state = viewState
            return this
        }

        val Initial: SearchResultViewState
            get() = SearchResultViewState()

        val Hide: SearchResultViewState
            get() = SearchResultViewState()

        val Searching: SearchResultViewState
            get() = state.copy(
                showProgress = state.searchResult.isEmpty(),
                showEmpty = false,
                showResult = state.searchResult.isNotEmpty(),
                showError = false,
                error = null
            )

        fun Error(message: String): SearchResultViewState =
            state.copy(
                showProgress = false,
                showEmpty = false,
                showResult = false,
                showError = true,
                error = message
            )

        fun ResultLoaded(characters: List<CharacterModel>): SearchResultViewState =
            state.copy(
                searchResult = characters,
                showProgress = false,
                showEmpty = characters.isEmpty(),
                showResult = characters.isNotEmpty(),
                showError = false,
                error = null
            )
    }
}
