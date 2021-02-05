package com.ezike.tobenna.starwarssearch.lib_character_search.data.fakes

import com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.cache.CharacterDetailCache
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterDetailEntity

class FakeCharacterDetailCache : CharacterDetailCache {

    private val cache = LinkedHashMap<String, CharacterDetailEntity>()

    override suspend fun saveCharacter(characterDetailEntity: CharacterDetailEntity) {
        cache[characterDetailEntity.url] = characterDetailEntity
    }

    override suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity? {
        return cache[characterUrl]
    }
}
