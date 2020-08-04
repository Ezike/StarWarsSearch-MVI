package com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent

sealed class SearchViewIntent : ViewIntent
sealed class SearchHistoryViewIntent : SearchViewIntent() {
    object LoadSearchHistory : SearchHistoryViewIntent()
    object ClearSearchHistory : SearchHistoryViewIntent()
}
sealed class SearchCharacterViewIntent : SearchViewIntent() {
    data class Search(val query: String) : SearchCharacterViewIntent()
    data class SaveSearch(val character: CharacterModel) : SearchCharacterViewIntent()
}
