package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.character_search.ui.search.LoadSearchHistory
import com.ezike.tobenna.starwarssearch.character_search.views.search.RetrySearchIntent
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchIntent
import com.ezike.tobenna.starwarssearch.character_search.views.search.UpdateHistoryIntent
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.usecase.search.SearchCharacters
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.ClearSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.GetSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.SaveSearch
import com.ezike.tobenna.starwarssearch.presentation.mvi.InvalidViewIntentException
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import com.ezike.tobenna.starwarssearch.character_search.views.search.ClearSearchHistoryIntent as ClearSearchHistoryIntent
import com.ezike.tobenna.starwarssearch.character_search.views.search.SaveSearchIntent as SaveSearchIntent

class SearchViewIntentProcessor @Inject constructor(
    private val searchCharacters: SearchCharacters,
    private val saveSearch: SaveSearch,
    private val getSearchHistory: GetSearchHistory,
    private val clearSearchHistory: ClearSearchHistory,
    private val modelMapper: CharacterModelMapper
) : SearchIntentProcessor {

    override fun intentToResult(viewIntent: ViewIntent): Flow<SearchViewResult> {
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

    private fun clearCache(): Flow<SearchViewResult.LoadedHistory> {
        return flow {
            clearSearchHistory()
            emit(SearchViewResult.LoadedHistory(emptyList()))
        }.catch { error ->
            error.printStackTrace()
        }
    }

    private fun cacheCharacter(character: CharacterModel): Flow<SearchViewResult> {
        return flow<SearchViewResult> {
            saveSearch(modelMapper.mapToDomain(character))
        }.catch { error ->
            error.printStackTrace()
        }
    }

    private fun updateCache(character: CharacterModel): Flow<SearchViewResult> {
        return flow {
            saveSearch(modelMapper.mapToDomain(character))
            emitAll(loadSearchHistory())
        }.catch { error ->
            error.printStackTrace()
        }
    }

    private fun loadSearchHistory(): Flow<SearchViewResult> {
        return getSearchHistory()
            .map { characters ->
                SearchViewResult.LoadedHistory(characters)
            }.catch { error ->
                error.printStackTrace()
            }
    }

    private fun executeSearch(query: String): Flow<SearchViewResult> {
        if (query.isBlank()) {
            return loadSearchHistory()
        }
        return searchCharacters(query.trim())
            .map<List<Character>, SearchCharacterResult> { characters ->
                SearchCharacterResult.Success(characters)
            }.onStart {
                emit(SearchCharacterResult.Searching)
            }.catch { throwable ->
                emit(SearchCharacterResult.Error(throwable))
            }
    }
}
