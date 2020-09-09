package com.ezike.tobenna.starwarssearch.character_search.views.search

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchHistoryBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter.SearchHistoryAdapter
import com.ezike.tobenna.starwarssearch.core.ext.show
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

object ClearSearchHistoryIntent : ViewIntent
data class UpdateHistoryIntent(val character: CharacterModel) : ViewIntent

class SearchHistoryView(
    private val binding: LayoutSearchHistoryBinding,
    dispatch: DispatchIntent,
    navigationAction: (CharacterModel) -> Unit = {}
) : UIComponent<SearchHistoryViewState>() {

    private val searchHistoryAdapter: SearchHistoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchHistoryAdapter { model ->
            dispatch(UpdateHistoryIntent(model))
            navigationAction(model)
        }
    }

    init {
        binding.clearHistory.setOnClickListener { dispatch(ClearSearchHistoryIntent) }
        binding.searchHistoryRv.adapter = searchHistoryAdapter
    }

    override fun render(state: SearchHistoryViewState) {
        searchHistoryAdapter.submitList(state.history)
        binding.run {
            recentSearchGroup.isVisible = state.isVisible && state.history.isNotEmpty()
            searchHistoryRv.show = state.isVisible && state.history.isNotEmpty()
            searchHistoryPrompt.isVisible = state.isVisible && state.history.isEmpty()
        }
    }
}

data class SearchHistoryViewState private constructor(
    val history: List<CharacterModel> = emptyList(),
    val isVisible: Boolean = false
) : ViewState {

    inline fun state(transform: Factory.() -> SearchHistoryViewState): SearchHistoryViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: SearchHistoryViewState

        operator fun invoke(viewState: SearchHistoryViewState): Factory {
            state = viewState
            return this
        }

        val init: SearchHistoryViewState
            get() = SearchHistoryViewState()

        val hide: SearchHistoryViewState
            get() = SearchHistoryViewState()

        fun success(history: List<CharacterModel>): SearchHistoryViewState =
            state.copy(history = history, isVisible = true)
    }
}
