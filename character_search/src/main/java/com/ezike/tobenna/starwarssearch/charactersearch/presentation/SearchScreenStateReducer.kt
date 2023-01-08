package com.ezike.tobenna.starwarssearch.charactersearch.presentation

import com.ezike.tobenna.starwarssearch.charactersearch.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchScreenResult.LoadedHistory
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchScreenResult.SearchCharacterResult.LoadedSearchResult
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchScreenResult.SearchCharacterResult.SearchError
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchScreenResult.SearchCharacterResult.Searching
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.viewstate.SearchScreenState
import com.ezike.tobenna.starwarssearch.charactersearch.ui.views.history.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.charactersearch.ui.views.result.SearchResultViewState
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import javax.inject.Inject

internal class SearchScreenStateReducer @Inject constructor(
    private val characterModelMapper: CharacterModelMapper
) : SearchStateReducer {

    override fun reduce(
        oldState: SearchScreenState,
        result: SearchScreenResult
    ): SearchScreenState = when (result) {
        is LoadedHistory -> {
            val data =
                characterModelMapper.mapToModelList(result.searchHistory)
            val state = SearchHistoryViewState.DataLoaded(data = data)
            SearchScreenState.HistoryView(state)
        }
        Searching -> {
            val state = SearchResultViewState.Searching(
                data = oldState.searchResultState.resultState.data
            )
            SearchScreenState.ResultView(state = state)
        }
        is SearchError -> {
            val state = SearchResultViewState.Error(
                message = result.throwable.errorMessage
            )
            SearchScreenState.ResultView(state = state)
        }
        is LoadedSearchResult -> {
            val data =
                characterModelMapper.mapToModelList(result.characters)
            val state = SearchResultViewState.DataLoaded(data = data)
            SearchScreenState.ResultView(state = state)
        }
    }
}
