package com.ezike.tobenna.starwarssearch.libcharactersearch.data.repository

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.cache.SearchHistoryCache
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper.CharacterEntityMapper
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryCache: SearchHistoryCache,
    private val characterEntityMapper: CharacterEntityMapper
) : SearchHistoryRepository {

    override suspend fun saveSearch(character: Character) {
        val characterEntity: CharacterEntity = characterEntityMapper.mapToEntity(character)
        searchHistoryCache.saveSearch(characterEntity)
    }

    override fun getSearchHistory(): Flow<List<Character>> {
        return flow {
            val searchHistory: List<CharacterEntity> = searchHistoryCache.getSearchHistory()
            emit(searchHistory.map(characterEntityMapper::mapFromEntity))
        }
    }

    override suspend fun clearSearchHistory() {
        searchHistoryCache.clearSearchHistory()
    }
}
