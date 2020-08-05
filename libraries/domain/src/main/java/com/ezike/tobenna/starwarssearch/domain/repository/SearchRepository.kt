package com.ezike.tobenna.starwarssearch.domain.repository

import com.ezike.tobenna.starwarssearch.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchCharacters(characterName: String): Flow<List<Character>>
}
