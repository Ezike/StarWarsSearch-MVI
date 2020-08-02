package com.ezike.tobenna.starwarssearch.cache.cacheImpl

import com.ezike.tobenna.starwarssearch.cache.SearchHistoryDao
import com.ezike.tobenna.starwarssearch.cache.entity.CharacterCacheModel
import com.ezike.tobenna.starwarssearch.cache.mapper.CharacterCacheModelMapper
import com.ezike.tobenna.starwarssearch.data.contract.SearchHistoryCache
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SearchHistoryCacheImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
    private val characterCacheModelMapper: CharacterCacheModelMapper
) : SearchHistoryCache {

    override suspend fun saveSearch(character: CharacterEntity) {
        val characterModel: CharacterCacheModel = characterCacheModelMapper.mapToModel(character)
        characterModel.timeSent = System.currentTimeMillis()
        searchHistoryDao.insertSearch(characterModel)
    }

    override fun getSearchHistory(): Flow<List<CharacterEntity>> {
        val characterModels: Flow<List<CharacterCacheModel>> = searchHistoryDao.recentSearches
        return characterModels.distinctUntilChanged()
            .map(characterCacheModelMapper::mapToEntityList)
    }

    override suspend fun clearSearchHistory() {
        searchHistoryDao.clearHistory()
    }
}
