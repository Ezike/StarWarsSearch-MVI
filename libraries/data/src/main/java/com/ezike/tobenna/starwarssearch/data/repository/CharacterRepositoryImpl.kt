package com.ezike.tobenna.starwarssearch.data.repository

import com.ezike.tobenna.starwarssearch.data.contract.CharacterRemote
import com.ezike.tobenna.starwarssearch.data.mapper.CharacterEntityMapper
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.repository.CharacterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemote: CharacterRemote,
    private val characterEntityMapper: CharacterEntityMapper
) : CharacterRepository {

    override fun searchCharacters(characterName: String): Flow<List<Character>> {
        return flow {
            val characters: List<CharacterEntity> = characterRemote.searchCharacters(characterName)
            emit(characterEntityMapper.mapFromEntityList(characters))
        }
    }
}
