package com.ezike.tobenna.starwarssearch.charactersearch.data

import com.ezike.tobenna.starwarssearch.cache.model.CharacterCacheModel
import com.ezike.tobenna.starwarssearch.cache.room.CharacterDetailDao
import com.ezike.tobenna.starwarssearch.cache.room.SearchHistoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal interface SearchRepository {
    fun searchCharacters(characterName: String): Flow<List<CharacterEntity>>
    fun getSearchHistory(): Flow<List<CharacterEntity>>
    suspend fun saveSearch(character: CharacterEntity)
    suspend fun clearSearchHistory()
}

internal class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val searchHistoryDao: SearchHistoryDao,
    private val characterDetailDao: CharacterDetailDao,
) : SearchRepository {

    override fun searchCharacters(characterName: String): Flow<List<CharacterEntity>> =
        flow { emit(search(characterName)) }

    override suspend fun saveSearch(character: CharacterEntity) {
        val characterModel = CharacterCacheModel(
            name = character.name,
            birthYear = character.birthYear,
            height = character.height,
            url = character.url
        )
        characterModel.lastUpdated = System.currentTimeMillis()
        searchHistoryDao.insertSearch(characterModel)
    }

    override fun getSearchHistory(): Flow<List<CharacterEntity>> =
        flow {
            val characterModels = searchHistoryDao
                .recentSearches()
                .map { model ->
                    CharacterEntity(
                        model.name,
                        model.birthYear,
                        model.height,
                        model.url
                    )
                }
            emit(characterModels)
        }

    override suspend fun clearSearchHistory() {
        searchHistoryDao.clearHistory()
        characterDetailDao.clearData()
    }

    private suspend fun search(characterName: String): List<CharacterEntity> =
        buildList {
            val characters: CharacterSearchResponse = apiService.searchCharacters(characterName)
            addAll(characters.results)
            nextPage(characters.next, this::addAll)
        }.map { model -> CharacterEntity(model.name, model.birth_year, model.height, model.url) }

    private suspend fun nextPage(url: String?, result: (List<CharacterRemoteModel>) -> Unit) {
        if (url == null) {
            return
        }
        val nextPage: CharacterSearchResponse = apiService.nextSearchPage(url)
        result(nextPage.results)
        nextPage(nextPage.next, result)
    }
}
