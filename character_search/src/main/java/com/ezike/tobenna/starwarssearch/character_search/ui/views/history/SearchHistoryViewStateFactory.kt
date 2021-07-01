package com.ezike.tobenna.starwarssearch.character_search.ui.views.history

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel

inline fun SearchHistoryViewState.state(
    transform: SearchHistoryViewStateFactory.() -> SearchHistoryViewState
): SearchHistoryViewState = transform(SearchHistoryViewStateFactory(this))

object SearchHistoryViewStateFactory {

    private lateinit var state: SearchHistoryViewState

    operator fun invoke(
        viewState: SearchHistoryViewState
    ): SearchHistoryViewStateFactory {
        state = viewState
        return this
    }

    val initialState: SearchHistoryViewState
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
