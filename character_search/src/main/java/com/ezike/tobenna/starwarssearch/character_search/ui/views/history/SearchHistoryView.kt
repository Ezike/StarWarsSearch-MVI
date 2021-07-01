package com.ezike.tobenna.starwarssearch.character_search.ui.views.history

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchHistoryBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.ClearSearchHistoryIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.UpdateHistoryIntent
import com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter.SearchHistoryAdapter
import com.ezike.tobenna.starwarssearch.core.ext.init
import com.ezike.tobenna.starwarssearch.core.ext.show
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class SearchHistoryView(
    private val view: LayoutSearchHistoryBinding,
    navigationAction: (CharacterModel) -> Unit
) : UIComponent<SearchHistoryViewState>() {

    private val searchHistoryAdapter: SearchHistoryAdapter by init {
        SearchHistoryAdapter { model ->
            sendIntent(UpdateHistoryIntent(model))
            navigationAction(model)
        }
    }

    init {
        view.clearHistory.setOnClickListener { sendIntent(ClearSearchHistoryIntent) }
        view.searchHistoryRv.adapter = searchHistoryAdapter
    }

    override fun render(state: SearchHistoryViewState) {
        searchHistoryAdapter.submitList(state.history)
        view.run {
            recentSearchGroup.isVisible = state.showRecentSearchGroup
            searchHistoryRv.show = state.showHistory
            searchHistoryPrompt.isVisible = state.showHistoryPrompt
        }
    }
}
