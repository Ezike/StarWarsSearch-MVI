package com.ezike.tobenna.starwarssearch.data.contract

import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity

interface CharacterRemote {
    suspend fun searchCharacters(characterName: String): List<CharacterEntity>
}
