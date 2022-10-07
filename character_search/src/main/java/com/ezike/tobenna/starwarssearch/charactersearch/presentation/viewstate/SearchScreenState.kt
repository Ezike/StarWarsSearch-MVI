package com.ezike.tobenna.starwarssearch.charactersearch.presentation.viewstate

import com.ezike.tobenna.starwarssearch.charactersearch.ui.views.history.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.charactersearch.ui.views.result.SearchResultViewState
import com.ezike.tobenna.starwarssearch.presentation.base.ScreenState

sealed class SearchScreenState(
    val searchHistoryState: SearchHistoryViewState,
    val searchResultState: SearchResultViewState
) : ScreenState {

    object Initial : SearchScreenState(
        searchHistoryState = SearchHistoryViewState.Initial,
        searchResultState = SearchResultViewState.Initial
    )

    data class HistoryView(
        val state: SearchHistoryViewState
    ) : SearchScreenState(
        searchHistoryState = state,
        searchResultState = SearchResultViewState.Hide
    )

    data class ResultView(
        val state: SearchResultViewState
    ) : SearchScreenState(
        searchHistoryState = SearchHistoryViewState.Hide,
        searchResultState = state
    )
}
