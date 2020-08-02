package com.ezike.tobenna.starwarssearch.character_search.presentation.mvi

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

sealed class SearchViewState : ViewState {
    object Idle : SearchViewState()
    sealed class SearchHistoryViewState : SearchViewState() {
        data class SearchHistoryLoaded(val history: List<CharacterModel>) : SearchViewState()
        object SearchHistoryEmpty : SearchViewState()
    }

    sealed class SearchCharacterViewState : SearchViewState() {
        object Searching : SearchCharacterViewState()
        data class SearchResultLoaded(val characters: List<CharacterModel>) :
            SearchCharacterViewState()

        data class Error(val message: String) : SearchCharacterViewState()
    }
}
