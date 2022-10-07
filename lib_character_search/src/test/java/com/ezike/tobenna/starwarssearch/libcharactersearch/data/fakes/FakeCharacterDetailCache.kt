package com.ezike.tobenna.starwarssearch.libcharactersearch.data.fakes

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.cache.CharacterDetailCache
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterDetailEntity

internal class FakeCharacterDetailCache : CharacterDetailCache {

    private val cache = LinkedHashMap<String, CharacterDetailEntity>()

    override suspend fun saveCharacter(characterDetailEntity: CharacterDetailEntity) {
        cache[characterDetailEntity.url] = characterDetailEntity
    }

    override suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity? {
        return cache[characterUrl]
    }
}
