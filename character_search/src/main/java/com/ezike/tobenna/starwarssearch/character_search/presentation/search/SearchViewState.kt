package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchResultViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ScreenState

data class SearchViewState private constructor(
    val searchHistoryState: SearchHistoryViewState = SearchHistoryViewState.init,
    val searchResultState: SearchResultViewState = SearchResultViewState.init
) : ScreenState {

    inline fun translateTo(transform: Factory.() -> SearchViewState): SearchViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: SearchViewState

        operator fun invoke(viewState: SearchViewState): Factory {
            state = viewState
            return this
        }

        val init: SearchViewState
            get() = SearchViewState()

        fun searchHistoryState(transform: SearchHistoryViewState.Factory.() -> SearchHistoryViewState): SearchViewState {
            return state.copy(
                searchResultState = state.searchResultState.state { hide },
                searchHistoryState = state.searchHistoryState.state(transform)
            )
        }

        fun searchResultState(transform: SearchResultViewState.Factory.() -> SearchResultViewState): SearchViewState {
            return state.copy(
                searchResultState = state.searchResultState.state(transform),
                searchHistoryState = state.searchHistoryState.state { hide }
            )
        }
    }
}
