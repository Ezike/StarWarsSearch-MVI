package com.ezike.tobenna.starwarssearch.character_search.views.search

import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchHistoryBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter.SearchHistoryAdapter
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

data class SearchHistoryViewState(
    val history: List<CharacterModel> = emptyList(),
    val isVisible: Boolean = false
) : ViewState {

    val hide: SearchHistoryViewState
        get() = SearchHistoryViewState()

    fun success(history: List<CharacterModel>): SearchHistoryViewState =
        this.copy(history = history, isVisible = true)
}

object ClearSearchHistory : ViewIntent
data class UpdateHistory(val character: CharacterModel) : ViewIntent

class SearchHistoryView(
    private val binding: LayoutSearchHistoryBinding,
    dispatch: DispatchIntent,
    navigationAction: (CharacterModel) -> Unit = {}
) {

    private val searchHistoryAdapter: SearchHistoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchHistoryAdapter { model ->
            dispatch(UpdateHistory(model))
            navigationAction(model)
        }
    }

    init {
        binding.clearHistory.setOnClickListener { dispatch(ClearSearchHistory) }
        binding.searchHistoryRv.adapter = searchHistoryAdapter
    }

    fun render(state: SearchHistoryViewState) {
        searchHistoryAdapter.submitList(state.history)
        binding.run {
            searchHistoryPrompt.isVisible = state.isVisible && state.history.isEmpty()
            recentSearchGroup.isVisible = state.isVisible && state.history.isNotEmpty()
            searchHistoryRv.isInvisible = !state.isVisible && state.history.isEmpty()
        }
    }
}
