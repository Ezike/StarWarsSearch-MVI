package com.ezike.tobenna.starwarssearch.character_search.presentation.mvi

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

sealed class SearchViewState : ViewState {
    object Idle : SearchViewState()
    sealed class SearchHistoryViewState : SearchViewState() {
        data class SearchHistoryLoaded(val history: List<String>) : SearchViewState()
        object SearchHistoryEmpty : SearchViewState()
    }

    sealed class SearchResultViewState : SearchViewState() {
        object Searching : SearchResultViewState()
        data class SearchResultLoaded(val characters: List<CharacterModel>) :
            SearchResultViewState()

        data class Error(val message: String) : SearchResultViewState()
    }
}
