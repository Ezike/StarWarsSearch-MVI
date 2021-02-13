package com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchCharacters(characterName: String): Flow<List<Character>>
}
