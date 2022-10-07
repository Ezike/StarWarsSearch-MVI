package com.ezike.tobenna.starwarssearch.charactersearch.presentation

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.presentation.base.ViewResult

sealed class SearchScreenResult : ViewResult {
    data class LoadedHistory(val searchHistory: List<Character>) : SearchScreenResult()
    sealed class SearchCharacterResult : SearchScreenResult() {
        object Searching : SearchCharacterResult()
        data class SearchError(val throwable: Throwable) : SearchCharacterResult()
        data class LoadedSearchResult(val characters: List<Character>) : SearchCharacterResult()
    }
}
