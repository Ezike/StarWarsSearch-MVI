package com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.cache

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterDetailEntity

internal interface CharacterDetailCache {
    suspend fun saveCharacter(characterDetailEntity: CharacterDetailEntity)
    suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity?
}
