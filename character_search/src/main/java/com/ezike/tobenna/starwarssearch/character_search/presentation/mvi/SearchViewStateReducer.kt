package com.ezike.tobenna.starwarssearch.character_search.presentation.mvi

import com.ezike.tobenna.starwarssearch.character_search.di.SearchStateReducer
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewResult.SearchHistoryResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState.SearchCharacterViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import javax.inject.Inject

class SearchViewStateReducer @Inject constructor(
    private val characterModelMapper: CharacterModelMapper
) : SearchStateReducer {

    override fun reduce(previous: SearchViewState, result: SearchViewResult): SearchViewState {
        return when (result) {
            SearchHistoryResult.Empty -> SearchHistoryViewState.SearchHistoryEmpty
            is SearchHistoryResult.Success -> SearchHistoryViewState.SearchHistoryLoaded(
                characterModelMapper.mapToModelList(result.searchHistory)
            )
            SearchCharacterResult.Searching -> SearchCharacterViewState.Searching
            is SearchCharacterResult.Error -> SearchCharacterViewState.Error(result.throwable.errorMessage)
            is SearchCharacterResult.Success -> SearchCharacterViewState.SearchResultLoaded(
                characterModelMapper.mapToModelList(result.characters)
            )
        }
    }
}
