package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchScreenResult.LoadedHistory
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchScreenResult.SearchCharacterResult.Error
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchScreenResult.SearchCharacterResult.Searching
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchScreenResult.SearchCharacterResult.Success
import com.ezike.tobenna.starwarssearch.character_search.presentation.viewstate.SearchScreenState
import com.ezike.tobenna.starwarssearch.character_search.presentation.viewstate.translateTo
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
            is LoadedHistory -> oldState.translateTo {
                searchHistoryState {
                    DataLoaded(characterModelMapper.mapToModelList(result.searchHistory))
                }
            }
            Searching -> oldState.translateTo {
                searchResultState { Searching }
            }
            is Error -> oldState.translateTo {
                searchResultState { Error(result.throwable.errorMessage) }
            }
            is Success -> oldState.translateTo {
                val characters: List<CharacterModel> =
                    characterModelMapper.mapToModelList(result.characters)
                searchResultState { ResultLoaded(characters) }
            }
        }
    }
}
