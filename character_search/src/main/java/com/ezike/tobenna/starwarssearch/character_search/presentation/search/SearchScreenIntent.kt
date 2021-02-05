package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewIntent

sealed interface SearchScreenIntent : ViewIntent
data class RetrySearchIntent(val query: String) : SearchScreenIntent
data class SaveSearchIntent(val character: CharacterModel) : SearchScreenIntent
data class SearchIntent(val query: String) : SearchScreenIntent
object ClearSearchHistoryIntent : SearchScreenIntent
data class UpdateHistoryIntent(val character: CharacterModel) : SearchScreenIntent
object LoadSearchHistory : SearchScreenIntent
