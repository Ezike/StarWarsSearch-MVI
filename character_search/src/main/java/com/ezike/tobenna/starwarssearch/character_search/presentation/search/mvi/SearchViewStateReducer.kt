package com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi

import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateReducer
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import javax.inject.Inject

class SearchViewStateReducer @Inject constructor(
    private val characterModelMapper: CharacterModelMapper
) : SearchStateReducer {

    override fun reduce(previous: SearchViewState, result: SearchViewResult): SearchViewState {
        return when (result) {
            is SearchViewResult.LoadedHistory -> previous.translateTo {
                searchHistoryState {
                    success(characterModelMapper.mapToModelList(result.searchHistory))
                }
            }
            SearchCharacterResult.Searching -> previous.translateTo {
                searchResultState { searching }
            }
            is SearchCharacterResult.Error -> previous.translateTo {
                searchResultState { error(result.throwable.errorMessage) }
            }
            is SearchCharacterResult.Success -> previous.translateTo {
                searchResultState { success(characterModelMapper.mapToModelList(result.characters)) }
            }
        }
    }
}
