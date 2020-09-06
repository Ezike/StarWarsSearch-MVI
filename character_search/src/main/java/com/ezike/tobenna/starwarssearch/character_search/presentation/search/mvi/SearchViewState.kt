package com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi

import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchResultViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.ScreenState

data class SearchViewState(
    val searchHistoryState: SearchHistoryViewState = SearchHistoryViewState(),
    val searchResultState: SearchResultViewState = SearchResultViewState()
) : ScreenState {

    fun history(state: SearchHistoryViewState.() -> SearchHistoryViewState): SearchViewState {
        return this.copy(
            searchResultState = searchResultState.hide,
            searchHistoryState = searchHistoryState.state()
        )
    }

    fun searchResult(state: SearchResultViewState.() -> SearchResultViewState): SearchViewState {
        return this.copy(
            searchResultState = searchResultState.state(),
            searchHistoryState = searchHistoryState.hide
        )
    }
}
