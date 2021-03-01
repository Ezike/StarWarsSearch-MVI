package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateReducer
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchScreenResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import javax.inject.Inject

class SearchScreenStateReducer @Inject constructor(
    private val characterModelMapper: CharacterModelMapper
) : SearchStateReducer {

    override fun reduce(
        oldState: SearchScreenState,
        result: SearchScreenResult
    ): SearchScreenState {
        return when (result) {
            is SearchScreenResult.LoadedHistory -> oldState.translateTo {
                searchHistoryState {
                    DataLoaded(characterModelMapper.mapToModelList(result.searchHistory))
                }
            }
            SearchCharacterResult.Searching -> oldState.translateTo {
                searchResultState { Searching }
            }
            is SearchCharacterResult.Error -> oldState.translateTo {
                searchResultState { Error(result.throwable.errorMessage) }
            }
            is SearchCharacterResult.Success -> oldState.translateTo {
                val characters: List<CharacterModel> =
                    characterModelMapper.mapToModelList(result.characters)
                searchResultState { ResultLoaded(characters) }
            }
        }
    }
}
