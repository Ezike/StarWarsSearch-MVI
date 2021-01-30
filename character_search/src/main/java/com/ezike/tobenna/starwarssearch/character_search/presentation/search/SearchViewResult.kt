package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewResult

sealed class SearchViewResult : ViewResult {
    data class LoadedHistory(val searchHistory: List<Character>) : SearchViewResult()
    sealed class SearchCharacterResult : SearchViewResult() {
        object Searching : SearchCharacterResult()
        data class Error(val throwable: Throwable) : SearchCharacterResult()
        data class Success(val characters: List<Character>) : SearchCharacterResult()
    }
}
