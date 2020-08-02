package com.ezike.tobenna.starwarssearch.character_search.presentation.mvi

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent

sealed class SearchViewIntent : ViewIntent {
    object LoadSearchHistory : SearchViewIntent()
    object ClearSearchHistory : SearchViewIntent()
    data class Search(val query: String) : SearchViewIntent()
    data class SaveSearch(val character: CharacterModel) : SearchViewIntent()
}
