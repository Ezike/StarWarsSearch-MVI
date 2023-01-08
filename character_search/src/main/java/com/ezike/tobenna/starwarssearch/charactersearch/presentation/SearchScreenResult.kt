package com.ezike.tobenna.starwarssearch.charactersearch.presentation

import com.ezike.tobenna.starwarssearch.charactersearch.data.CharacterEntity
import com.ezike.tobenna.starwarssearch.presentation.base.ViewResult

internal sealed class SearchScreenResult : ViewResult {
    data class LoadedHistory(val searchHistory: List<CharacterEntity>) : SearchScreenResult()
    sealed class SearchCharacterResult : SearchScreenResult() {
        object Searching : SearchCharacterResult()
        data class SearchError(val throwable: Throwable) : SearchCharacterResult()
        data class LoadedSearchResult(val characters: List<CharacterEntity>) :
            SearchCharacterResult()
    }
}
