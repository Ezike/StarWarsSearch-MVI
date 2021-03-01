package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchResultViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ScreenState

data class SearchScreenState private constructor(
    val searchHistoryState: SearchHistoryViewState = SearchHistoryViewState.Initial,
    val searchResultState: SearchResultViewState = SearchResultViewState.Initial
) : ScreenState {

    inline fun translateTo(transform: Factory.() -> SearchScreenState): SearchScreenState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: SearchScreenState

        operator fun invoke(viewState: SearchScreenState): Factory {
            state = viewState
            return this
        }

        val Initial: SearchScreenState
            get() = SearchScreenState()

        fun searchHistoryState(
            transform: SearchHistoryViewState.Factory.() -> SearchHistoryViewState
        ): SearchScreenState {
            return state.copy(
                searchResultState = state.searchResultState.state { Hide },
                searchHistoryState = state.searchHistoryState.state(transform)
            )
        }

        fun searchResultState(
            transform: SearchResultViewState.Factory.() -> SearchResultViewState
        ): SearchScreenState {
            return state.copy(
                searchResultState = state.searchResultState.state(transform),
                searchHistoryState = state.searchHistoryState.state { Hide }
            )
        }
    }
}
