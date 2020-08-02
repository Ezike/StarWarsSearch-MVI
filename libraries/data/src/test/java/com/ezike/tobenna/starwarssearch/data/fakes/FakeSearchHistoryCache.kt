package com.ezike.tobenna.starwarssearch.data.fakes

import com.ezike.tobenna.starwarssearch.data.contract.SearchHistoryCache
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSearchHistoryCache : SearchHistoryCache {

    private val cache = LinkedHashMap<String, CharacterEntity>()

    override suspend fun saveSearch(character: CharacterEntity) {
        cache[character.url] = character
    }

    override fun getSearchHistory(): Flow<List<CharacterEntity>> {
        return flowOf(cache.values.toList().reversed())
    }

    override suspend fun clearSearchHistory() {
        cache.clear()
    }
}
