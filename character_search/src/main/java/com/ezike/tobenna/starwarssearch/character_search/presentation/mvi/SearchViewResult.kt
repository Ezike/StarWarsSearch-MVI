package com.ezike.tobenna.starwarssearch.character_search.presentation.mvi

import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewResult

sealed class SearchViewResult : ViewResult {
    sealed class SearchHistoryResult : SearchViewResult() {
        object Empty : SearchHistoryResult()
        data class Success(val searchHistory: List<Character>) : SearchHistoryResult()
    }

    sealed class SearchCharacterResult : SearchViewResult() {
        object Searching : SearchCharacterResult()
        data class Error(val throwable: Throwable) : SearchCharacterResult()
        data class Success(val characters: List<Character>) : SearchCharacterResult()
    }
}
