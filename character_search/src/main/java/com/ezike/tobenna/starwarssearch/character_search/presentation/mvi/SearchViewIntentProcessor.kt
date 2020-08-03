package com.ezike.tobenna.starwarssearch.character_search.presentation.mvi

import com.ezike.tobenna.starwarssearch.character_search.di.SearchIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewResult.SearchHistoryResult
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.usecase.search.SearchCharacters
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.ClearSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.GetSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.SaveSearch
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class SearchViewIntentProcessor @Inject constructor(
    private val searchCharacters: SearchCharacters,
    private val saveSearch: SaveSearch,
    private val getSearchHistory: GetSearchHistory,
    private val clearSearchHistory: ClearSearchHistory,
    private val modelMapper: CharacterModelMapper
) : SearchIntentProcessor {

    override fun intentToResult(viewIntent: SearchViewIntent): Flow<SearchViewResult> {
        return when (viewIntent) {
            is SearchCharacterViewIntent.Search -> executeSearch(viewIntent.query)
            is SearchCharacterViewIntent.SaveSearch -> flow {
                saveSearch(modelMapper.mapToDomain(viewIntent.character))
            }
            SearchHistoryViewIntent.LoadSearchHistory -> loadSearchHistory()
            SearchHistoryViewIntent.ClearSearchHistory -> flow {
                clearSearchHistory()
                emit(SearchHistoryResult.Empty)
            }
        }
    }

    private fun loadSearchHistory(): Flow<SearchHistoryResult> {
        return getSearchHistory()
            .map { characters ->
                if (characters.isNotEmpty()) {
                    SearchHistoryResult.Success(characters)
                } else {
                    SearchHistoryResult.Empty
                }
            }.catch { error ->
                error.printStackTrace()
            }
    }

    private fun executeSearch(query: String): Flow<SearchViewResult> {
        if (query.trim().isBlank()) {
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
