package com.ezike.tobenna.starwarssearch.charactersearch.fakes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSearchHistoryRepository : SearchHistoryRepository {

    private val cache = LinkedHashMap<String, Character>()

    override suspend fun saveSearch(character: Character) {
        cache[character.url] = character
    }

    override fun getSearchHistory(): Flow<List<Character>> {
        return flowOf(cache.values.toList().reversed())
    }

    override suspend fun clearSearchHistory() {
        cache.clear()
    }
}
