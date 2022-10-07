package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun saveSearch(character: Character)
    fun getSearchHistory(): Flow<List<Character>>
    suspend fun clearSearchHistory()
}
