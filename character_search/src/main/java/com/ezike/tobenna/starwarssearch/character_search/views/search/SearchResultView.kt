package com.ezike.tobenna.starwarssearch.character_search.views.search

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchResultBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter.SearchResultAdapter
import com.ezike.tobenna.starwarssearch.core.ext.show
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent
import com.ezike.tobenna.starwarssearch.presentation_android.UIRenderer

data class RetrySearchIntent(val query: String) : ViewIntent
data class SaveSearchIntent(val character: CharacterModel) : ViewIntent

@Suppress("FunctionName")
fun SearchResultView(
    binding: LayoutSearchResultBinding,
    dispatch: DispatchIntent,
    query: () -> String,
    navigationAction: (CharacterModel) -> Unit = {}
): UIComponent<SearchResultViewState> {

    val searchResultAdapter: SearchResultAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchResultAdapter { model ->
            dispatch(SaveSearchIntent(model))
            navigationAction(model)
        }
    }

    binding.charactersRv.adapter = searchResultAdapter
    binding.errorState.onRetry { dispatch(RetrySearchIntent(query())) }

    return UIRenderer { state: SearchResultViewState ->
        searchResultAdapter.submitList(state.characters)
        binding.run {
            charactersRv.show = state.showRv
            progressBar.isVisible = state.showLoading
            emptyState.isVisible = state.showEmpty
            errorState.isVisible = state.showError
            errorState.setCaption(state.error)
        }
    }
}

data class SearchResultViewState private constructor(
    val characters: List<CharacterModel> = emptyList(),
    val isSearching: Boolean = false,
    val isVisible: Boolean = false,
    val error: String? = null
) : ViewState {

    inline fun state(transform: Factory.() -> SearchResultViewState): SearchResultViewState =
        transform(Factory(this))

    val showRv: Boolean
        get() = isVisible && characters.isNotEmpty()

    val showLoading: Boolean
        get() = isVisible && characters.isEmpty() && isSearching

    val showEmpty: Boolean
        get() = isVisible && !isSearching && error == null && characters.isEmpty()

    val showError: Boolean
        get() = isVisible && !isSearching && error != null

    companion object Factory {

        private lateinit var state: SearchResultViewState

        operator fun invoke(viewState: SearchResultViewState): Factory {
            state = viewState
            return this
        }

        val init: SearchResultViewState
            get() = SearchResultViewState()

        val hide: SearchResultViewState
            get() = SearchResultViewState()

        val searching: SearchResultViewState
            get() = state.copy(isVisible = true, error = null, isSearching = true)

        fun error(message: String): SearchResultViewState =
            state.copy(
                isVisible = true,
                error = message,
                isSearching = false,
                characters = emptyList()
            )

        fun success(characters: List<CharacterModel>): SearchResultViewState =
            state.copy(characters = characters, isVisible = true, error = null, isSearching = false)
    }
}
