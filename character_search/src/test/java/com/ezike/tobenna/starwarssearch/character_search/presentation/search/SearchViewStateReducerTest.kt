package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchScreenResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.ezike.tobenna.starwarssearch.testutils.ERROR_MSG
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SearchScreenStateReducerTest {

    private val mapper = CharacterModelMapper()
    private val reducer = SearchScreenStateReducer(mapper)

    @Test
    fun `check that emptySearchHistoryState is emitted when SearchHistoryResult is Empty`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val viewState: SearchScreenState = reducer.reduce(
                initialState,
                SearchScreenResult.LoadedHistory(emptyList())
            )
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchHistoryState { DataLoaded(emptyList()) }
                }
            )
        }
    }

    @Test
    fun `check that SearchHistoryLoadedState is emitted when SearchHistoryResult is Success`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val list: List<Character> = DummyData.characterList
            val viewState: SearchScreenState =
                reducer.reduce(initialState, SearchScreenResult.LoadedHistory(list))
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchHistoryState { DataLoaded(mapper.mapToModelList(list)) }
                }
            )
        }
    }

    @Test
    fun `check that SearchingState is emitted when SearchCharacterResult is Searching`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val viewState: SearchScreenState =
                reducer.reduce(initialState, SearchCharacterResult.Searching)
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchResultState { Searching }
                }
            )
        }
    }

    @Test
    fun `check that SearchResultLoadedState is emitted when SearchCharacterResult is Success`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val list: List<Character> = DummyData.characterList
            val viewState: SearchScreenState =
                reducer.reduce(initialState, SearchCharacterResult.Success(list))
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchResultState { ResultLoaded(mapper.mapToModelList(list)) }
                }
            )
        }
    }

    @Test
    fun `check that SearchResultErrorState is emitted when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val viewState: SearchScreenState =
                reducer.reduce(initialState, SearchCharacterResult.Error(Throwable(ERROR_MSG)))
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchResultState { Error(ERROR_MSG) }
                }
            )
        }
    }

    @Test
    fun `check that fall back error message is returned when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val viewState: SearchScreenState =
                reducer.reduce(initialState, SearchCharacterResult.Error(Throwable()))
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchResultState { Error("An error occurred") }
                }
            )
        }
    }
}
