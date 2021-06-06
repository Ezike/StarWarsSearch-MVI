package com.ezike.tobenna.starwarssearch.character_search.views.search

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchHistoryBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.ClearSearchHistoryIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.UpdateHistoryIntent
import com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter.SearchHistoryAdapter
import com.ezike.tobenna.starwarssearch.core.ext.init
import com.ezike.tobenna.starwarssearch.core.ext.show
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class SearchHistoryView(
    private val binding: LayoutSearchHistoryBinding,
    navigationAction: (CharacterModel) -> Unit
) : UIComponent<SearchHistoryViewState>() {

    private val searchHistoryAdapter: SearchHistoryAdapter by init {
        SearchHistoryAdapter { model ->
            sendIntent(UpdateHistoryIntent(model))
            navigationAction(model)
        }
    }

    init {
        binding.clearHistory.setOnClickListener { sendIntent(ClearSearchHistoryIntent) }
        binding.searchHistoryRv.adapter = searchHistoryAdapter
    }

    override fun render(state: SearchHistoryViewState) {
        searchHistoryAdapter.submitList(state.history)
        binding.run {
            recentSearchGroup.isVisible = state.showRecentSearchGroup
            searchHistoryRv.show = state.showHistory
            searchHistoryPrompt.isVisible = state.showHistoryPrompt
        }
    }
}

data class SearchHistoryViewState private constructor(
    val history: List<CharacterModel> = emptyList(),
    val showHistory: Boolean = false,
    val showRecentSearchGroup: Boolean = false,
    val showHistoryPrompt: Boolean = false
) : ViewState {

    inline fun state(transform: Factory.() -> SearchHistoryViewState): SearchHistoryViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: SearchHistoryViewState

        operator fun invoke(viewState: SearchHistoryViewState): Factory {
            state = viewState
            return this
        }

        val Initial: SearchHistoryViewState
            get() = SearchHistoryViewState()

        val Hide: SearchHistoryViewState
            get() = SearchHistoryViewState()

        fun DataLoaded(history: List<CharacterModel>): SearchHistoryViewState =
            state.copy(
                history = history,
                showHistory = history.isNotEmpty(),
                showRecentSearchGroup = history.isNotEmpty(),
                showHistoryPrompt = history.isEmpty()
            )
    }
}
