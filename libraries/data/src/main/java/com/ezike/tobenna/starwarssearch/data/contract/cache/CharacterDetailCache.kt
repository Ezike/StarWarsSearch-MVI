package com.ezike.tobenna.starwarssearch.data.contract.cache

import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity

interface CharacterDetailCache {
    suspend fun saveCharacter(characterDetailEntity: CharacterDetailEntity)
    suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity?
}
