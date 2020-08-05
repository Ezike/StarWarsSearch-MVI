package com.ezike.tobenna.starwarssearch.data.repository

import com.ezike.tobenna.starwarssearch.data.contract.remote.SearchRemote
import com.ezike.tobenna.starwarssearch.data.mapper.CharacterEntityMapper
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.repository.SearchRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl @Inject constructor(
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
