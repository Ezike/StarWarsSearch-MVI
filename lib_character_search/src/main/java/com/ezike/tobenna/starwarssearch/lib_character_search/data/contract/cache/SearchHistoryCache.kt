package com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.cache

import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity

internal interface SearchHistoryCache {
    suspend fun saveSearch(character: CharacterEntity)
    suspend fun getSearchHistory(): List<CharacterEntity>
    suspend fun clearSearchHistory()
}
