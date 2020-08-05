package com.ezike.tobenna.starwarssearch.data.fakes

import com.ezike.tobenna.starwarssearch.data.contract.cache.SearchHistoryCache
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity

class FakeSearchHistoryCache : SearchHistoryCache {

    private val cache = LinkedHashMap<String, CharacterEntity>()

    override suspend fun saveSearch(character: CharacterEntity) {
        cache[character.url] = character
    }

    override suspend fun getSearchHistory(): List<CharacterEntity> {
        return cache.values.toList().reversed()
    }

    override suspend fun clearSearchHistory() {
        cache.clear()
    }
}
