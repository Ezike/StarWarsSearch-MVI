package com.ezike.tobenna.starwarssearch.lib_character_search.data.repository

import com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.remote.SearchRemote
import com.ezike.tobenna.starwarssearch.lib_character_search.data.mapper.CharacterEntityMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val searchRemote: SearchRemote,
    private val characterEntityMapper: CharacterEntityMapper
) : SearchRepository {

    override fun searchCharacters(characterName: String): Flow<List<Character>> {
        return flow {
            val characters: List<CharacterEntity> = searchRemote.searchCharacters(characterName)
            emit(characterEntityMapper.mapFromEntityList(characters))
        }
    }
}
