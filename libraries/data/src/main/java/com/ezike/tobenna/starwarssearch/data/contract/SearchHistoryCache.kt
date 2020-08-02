package com.ezike.tobenna.starwarssearch.data.contract

import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface SearchHistoryCache {
    suspend fun saveSearch(character: CharacterEntity)
    fun getSearchHistory(): Flow<List<CharacterEntity>>
    suspend fun clearSearchHistory()
}
