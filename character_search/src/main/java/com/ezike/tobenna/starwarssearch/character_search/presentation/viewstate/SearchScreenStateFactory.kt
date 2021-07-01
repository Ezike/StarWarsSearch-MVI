package com.ezike.tobenna.starwarssearch.character_search.presentation.viewstate

import com.ezike.tobenna.starwarssearch.character_search.ui.views.history.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.views.history.SearchHistoryViewStateFactory
import com.ezike.tobenna.starwarssearch.character_search.ui.views.history.state
import com.ezike.tobenna.starwarssearch.character_search.ui.views.result.SearchResultViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.views.result.SearchResultViewStateFactory
import com.ezike.tobenna.starwarssearch.character_search.ui.views.result.state

inline fun SearchScreenState.translateTo(
    transform: SearchScreenStateFactory.() -> SearchScreenState
): SearchScreenState = transform(SearchScreenStateFactory(this))

object SearchScreenStateFactory {

    private lateinit var state: SearchScreenState

    operator fun invoke(
        viewState: SearchScreenState
    ): SearchScreenStateFactory {
        state = viewState
        return this
    }

    val initialState: SearchScreenState
        get() = SearchScreenState()

    fun searchHistoryState(
        transform: SearchHistoryViewStateFactory.() -> SearchHistoryViewState
    ): SearchScreenState = state.copy(
        searchResultState = state.searchResultState.state { Hide },
        searchHistoryState = state.searchHistoryState.state(transform)
    )

    fun searchResultState(
        transform: SearchResultViewStateFactory.() -> SearchResultViewState
    ): SearchScreenState = state.copy(
        searchResultState = state.searchResultState.state(transform),
        searchHistoryState = state.searchHistoryState.state { Hide }
    )
}
