package com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi

import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.character_search.ui.search.LoadSearchHistory
import com.ezike.tobenna.starwarssearch.character_search.views.search.RetrySearch
import com.ezike.tobenna.starwarssearch.character_search.views.search.Search
import com.ezike.tobenna.starwarssearch.character_search.views.search.UpdateHistory
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.usecase.search.SearchCharacters
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.ClearSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.GetSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.SaveSearch
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import com.ezike.tobenna.starwarssearch.character_search.views.search.ClearSearchHistory as ClearSearchHistoryIntent
import com.ezike.tobenna.starwarssearch.character_search.views.search.SaveSearch as SaveSearchIntent

class SearchViewIntentProcessor @Inject constructor(
    private val searchCharacters: SearchCharacters,
    private val saveSearch: SaveSearch,
    private val getSearchHistory: GetSearchHistory,
    private val clearSearchHistory: ClearSearchHistory,
    private val modelMapper: CharacterModelMapper
) : SearchIntentProcessor {

    override fun intentToResult(viewIntent: ViewIntent): Flow<SearchViewResult> {
        return when (viewIntent) {
            is Search -> executeSearch(viewIntent.query)
            is SaveSearchIntent -> cacheCharacter(viewIntent.character)
            is UpdateHistory -> cacheCharacter(viewIntent.character)
            LoadSearchHistory -> loadSearchHistory()
            ClearSearchHistoryIntent -> clearCache()
            is RetrySearch -> executeSearch(viewIntent.query)
            else -> throw IllegalArgumentException("Unknown intent $viewIntent")
        }
    }

    private fun clearCache(): Flow<SearchViewResult.LoadedHistory> {
        return flow {
            clearSearchHistory()
            emit(SearchViewResult.LoadedHistory(emptyList()))
        }
    }

    private fun cacheCharacter(character: CharacterModel): Flow<SearchViewResult> {
        return flow {
            saveSearch(modelMapper.mapToDomain(character))
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
