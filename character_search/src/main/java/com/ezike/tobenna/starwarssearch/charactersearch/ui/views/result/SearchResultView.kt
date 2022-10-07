package com.ezike.tobenna.starwarssearch.charactersearch.ui.views.result

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.charactersearch.databinding.LayoutSearchResultBinding
import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.RetrySearchIntent
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SaveSearchIntent
import com.ezike.tobenna.starwarssearch.charactersearch.ui.adapter.SearchResultAdapter
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
        searchResultAdapter.submitList(state.resultState.data)
        view.run {
            charactersRv.show = state.resultState.showResult
            progressBar.isVisible = state.showProgress
            emptyState.isVisible = state.showEmpty
            errorState.isVisible = state.errorState.showError
            errorState.setCaption(state.errorState.error)
        }
    }
}
