package com.ezike.tobenna.starwarssearch.character_search.ui.views.result

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchResultBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.RetrySearchIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.SaveSearchIntent
import com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter.SearchResultAdapter
import com.ezike.tobenna.starwarssearch.core.ext.init
import com.ezike.tobenna.starwarssearch.core.ext.show
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class SearchResultView(
    private val view: LayoutSearchResultBinding,
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
        view.charactersRv.adapter = searchResultAdapter
        view.errorState.onRetry { sendIntent(RetrySearchIntent(query())) }
    }

    override fun render(state: SearchResultViewState) {
        searchResultAdapter.submitList(state.searchResult)
        view.run {
            charactersRv.show = state.showResult
            progressBar.isVisible = state.showProgress
            emptyState.isVisible = state.showEmpty
            errorState.isVisible = state.showError
            errorState.setCaption(state.error)
        }
    }
}
