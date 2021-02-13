package com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.cache

import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterDetailEntity

internal interface CharacterDetailCache {
    suspend fun saveCharacter(characterDetailEntity: CharacterDetailEntity)
    suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity?
}
