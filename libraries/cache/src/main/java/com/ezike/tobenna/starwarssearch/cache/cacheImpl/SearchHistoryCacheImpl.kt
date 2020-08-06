package com.ezike.tobenna.starwarssearch.cache.cacheImpl

import com.ezike.tobenna.starwarssearch.cache.entity.CharacterCacheModel
import com.ezike.tobenna.starwarssearch.cache.mapper.CharacterCacheModelMapper
import com.ezike.tobenna.starwarssearch.cache.room.CharacterDetailDao
import com.ezike.tobenna.starwarssearch.cache.room.SearchHistoryDao
import com.ezike.tobenna.starwarssearch.data.contract.cache.SearchHistoryCache
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import javax.inject.Inject

class SearchHistoryCacheImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
    private val characterDetailDao: CharacterDetailDao,
    private val characterCacheModelMapper: CharacterCacheModelMapper
) : SearchHistoryCache {

    override suspend fun saveSearch(character: CharacterEntity) {
        val characterModel: CharacterCacheModel = characterCacheModelMapper.mapToModel(character)
        characterModel.timeSent = System.currentTimeMillis()
        searchHistoryDao.insertSearch(characterModel)
    }

    override suspend fun getSearchHistory(): List<CharacterEntity> {
        val characterModels: List<CharacterCacheModel> = searchHistoryDao.recentSearches()
        return characterModels.map(characterCacheModelMapper::mapToEntity)
    }

    override suspend fun clearSearchHistory() {
        searchHistoryDao.clearHistory()
        characterDetailDao.clearData()
    }
}
