package com.ezike.tobenna.starwarssearch.character_search.views.search

import androidx.annotation.UiThread
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchResultBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter.SearchResultAdapter
import com.ezike.tobenna.starwarssearch.core.ext.getImage
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.UIComponent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

data class SearchResultViewState(
    val characters: List<CharacterModel> = emptyList(),
    val isSearching: Boolean = false,
    val isVisible: Boolean = false,
    val error: String? = null
) : ViewState {

    val hide: SearchResultViewState
        get() = SearchResultViewState()

    val searching: SearchResultViewState
        get() = this.copy(isVisible = true, error = null, isSearching = true)

    fun error(message: String): SearchResultViewState =
        this.copy(isVisible = true, error = message, isSearching = false, characters = emptyList())

    fun success(characters: List<CharacterModel>): SearchResultViewState =
        this.copy(characters = characters, isVisible = true, error = null, isSearching = false)
}

data class RetrySearchIntent(val query: String) : ViewIntent
data class SaveSearchIntent(val character: CharacterModel) : ViewIntent

class SearchResultView(
    private val binding: LayoutSearchResultBinding,
    dispatch: DispatchIntent,
    query: () -> String,
    navigationAction: (CharacterModel) -> Unit = {}
) : UIComponent<SearchResultViewState>() {

    private val searchResultAdapter: SearchResultAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchResultAdapter { model ->
            dispatch(SaveSearchIntent(model))
            navigationAction(model)
        }
    }

    init {
        binding.charactersRv.adapter = searchResultAdapter
        binding.errorState.onRetry { dispatch(RetrySearchIntent(query())) }
    }

    @UiThread
    override fun render(state: SearchResultViewState) {
        searchResultAdapter.submitList(state.characters)
        binding.run {
            charactersRv.isInvisible =
                !state.isVisible && state.characters.isEmpty()
            progressBar.isVisible =
                state.isVisible && state.characters.isEmpty() && state.isSearching
            emptyState.isVisible =
                state.isVisible && !state.isSearching && state.characters.isEmpty() && state.error == null
            errorState.isVisible = state.isVisible && !state.isSearching && state.error != null
            errorState.setImage(root.context.getImage(R.drawable.ic_error_page_2))
            errorState.setCaption(state.error)
        }
    }
}
