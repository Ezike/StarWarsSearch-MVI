package com.ezike.tobenna.starwarssearch.libcharactersearch.data.fakes

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.cache.SearchHistoryCache
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity

internal class FakeSearchHistoryCache : SearchHistoryCache {

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
