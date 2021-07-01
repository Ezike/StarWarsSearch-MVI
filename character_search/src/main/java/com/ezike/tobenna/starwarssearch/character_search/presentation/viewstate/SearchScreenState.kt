package com.ezike.tobenna.starwarssearch.character_search.presentation.viewstate

import com.ezike.tobenna.starwarssearch.character_search.ui.views.history.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.views.history.SearchHistoryViewStateFactory
import com.ezike.tobenna.starwarssearch.character_search.ui.views.result.SearchResultViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.views.result.SearchResultViewStateFactory
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ScreenState

data class SearchScreenState(
    val searchHistoryState: SearchHistoryViewState = SearchHistoryViewStateFactory.initialState,
    val searchResultState: SearchResultViewState = SearchResultViewStateFactory.initialState
) : ScreenState
