package com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.remote

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity

internal interface SearchRemote {
    suspend fun searchCharacters(characterName: String): List<CharacterEntity>
}
