package com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.remote

import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity

internal interface SearchRemote {
    suspend fun searchCharacters(characterName: String): List<CharacterEntity>
}
