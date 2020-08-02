package com.ezike.tobenna.starwarssearch.data.repository

import com.ezike.tobenna.starwarssearch.data.contract.SearchHistoryCache
import com.ezike.tobenna.starwarssearch.data.mapper.CharacterEntityMapper
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.repository.SearchHistoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryCache: SearchHistoryCache,
    private val characterEntityMapper: CharacterEntityMapper
) : SearchHistoryRepository {

    override suspend fun saveSearch(character: Character) {
        val characterEntity: CharacterEntity = characterEntityMapper.mapToEntity(character)
        searchHistoryCache.saveSearch(characterEntity)
    }

    override fun getSearchHistory(): Flow<List<Character>> {
        val characters: Flow<List<CharacterEntity>> = searchHistoryCache.getSearchHistory()
        return characters.map(characterEntityMapper::mapFromEntityList)
    }

    override suspend fun clearSearchHistory() {
        searchHistoryCache.clearSearchHistory()
    }
}
