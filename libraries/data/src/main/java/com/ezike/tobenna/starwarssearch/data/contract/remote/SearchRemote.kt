package com.ezike.tobenna.starwarssearch.data.contract.remote

import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity

interface SearchRemote {
    suspend fun searchCharacters(characterName: String): List<CharacterEntity>
}
