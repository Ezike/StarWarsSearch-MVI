package com.ezike.tobenna.starwarssearch.charactersearch.presentation

import com.ezike.tobenna.starwarssearch.charactersearch.data.CharacterEntity
import com.ezike.tobenna.starwarssearch.charactersearch.data.SearchRepository
import com.ezike.tobenna.starwarssearch.charactersearch.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchScreenResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.presentation.base.InvalidViewIntentException
import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class SearchScreenIntentProcessor @Inject constructor(
    private val searchRepository: SearchRepository,
    private val modelMapper: CharacterModelMapper,
) : SearchIntentProcessor {

    override fun intentToResult(viewIntent: ViewIntent): Flow<SearchScreenResult> {
        return when (viewIntent) {
            is SearchIntent -> executeSearch(viewIntent.query)
            is SaveSearchIntent -> cacheCharacter(viewIntent.character)
            is UpdateHistoryIntent -> updateCache(viewIntent.character)
            LoadSearchHistory -> loadSearchHistory()
            ClearSearchHistoryIntent -> clearCache()
            is RetrySearchIntent -> executeSearch(viewIntent.query)
            else -> throw InvalidViewIntentException(viewIntent)
        }
    }

    private fun clearCache(): Flow<SearchScreenResult.LoadedHistory> {
        return flow {
            searchRepository.clearSearchHistory()
            emit(SearchScreenResult.LoadedHistory(emptyList()))
        }.catch { error ->
            error.printStackTrace()
        }
    }

    private fun cacheCharacter(character: CharacterModel): Flow<SearchScreenResult> {
        return flow<SearchScreenResult> {
            searchRepository.saveSearch(modelMapper.mapToDomain(character))
        }.catch { error ->
            error.printStackTrace()
        }
    }

    private fun updateCache(character: CharacterModel): Flow<SearchScreenResult> {
        return flow {
            searchRepository.saveSearch(modelMapper.mapToDomain(character))
            emitAll(loadSearchHistory())
        }.catch { error ->
            error.printStackTrace()
        }
    }

    private fun loadSearchHistory(): Flow<SearchScreenResult> {
        return searchRepository.getSearchHistory()
            .map { characters ->
                SearchScreenResult.LoadedHistory(characters)
            }.catch { error ->
                error.printStackTrace()
            }
    }

    private fun executeSearch(query: String): Flow<SearchScreenResult> {
        if (query.isBlank()) {
            return loadSearchHistory()
        }
        return searchRepository.searchCharacters(query.trim())
            .map<List<CharacterEntity>, SearchCharacterResult> { characters ->
                SearchCharacterResult.LoadedSearchResult(characters)
            }.onStart {
                emit(SearchCharacterResult.Searching)
            }.catch { throwable ->
                emit(SearchCharacterResult.SearchError(throwable))
            }
    }
}
