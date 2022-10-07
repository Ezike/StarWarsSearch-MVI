package com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.cache

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity

internal interface SearchHistoryCache {
    suspend fun saveSearch(character: CharacterEntity)
    suspend fun getSearchHistory(): List<CharacterEntity>
    suspend fun clearSearchHistory()
}
