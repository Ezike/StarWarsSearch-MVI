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
import com.ezike.tobenna.starwarssearch.presentation_android.UIRenderer

object ClearSearchHistoryIntent : ViewIntent
data class UpdateHistoryIntent(val character: CharacterModel) : ViewIntent

@Suppress("FunctionName")
fun SearchHistoryView(
    binding: LayoutSearchHistoryBinding,
    dispatch: DispatchIntent,
    navigationAction: (CharacterModel) -> Unit = {}
): UIComponent<SearchHistoryViewState> {

    val searchHistoryAdapter: SearchHistoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchHistoryAdapter { model ->
            dispatch(UpdateHistoryIntent(model))
            navigationAction(model)
        }
    }

    binding.clearHistory.setOnClickListener { dispatch(ClearSearchHistoryIntent) }
    binding.searchHistoryRv.adapter = searchHistoryAdapter

    return UIRenderer { (history: List<CharacterModel>, isVisible: Boolean) ->
        searchHistoryAdapter.submitList(history)
        binding.run {
            recentSearchGroup.isVisible = isVisible && history.isNotEmpty()
            searchHistoryRv.show = isVisible && history.isNotEmpty()
            searchHistoryPrompt.isVisible = isVisible && history.isEmpty()
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
