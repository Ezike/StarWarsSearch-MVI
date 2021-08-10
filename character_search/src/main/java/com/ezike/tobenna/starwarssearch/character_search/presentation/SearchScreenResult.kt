package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.ezike.tobenna.starwarssearch.presentation.base.ViewResult

sealed class SearchScreenResult : ViewResult {
    data class LoadedHistory(val searchHistory: List<Character>) : SearchScreenResult()
    sealed class SearchCharacterResult : SearchScreenResult() {
        object Searching : SearchCharacterResult()
        data class Error(val throwable: Throwable) : SearchCharacterResult()
        data class Success(val characters: List<Character>) : SearchCharacterResult()
    }
}
