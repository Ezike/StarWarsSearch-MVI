package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.ezike.tobenna.starwarssearch.testutils.ERROR_MSG
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SearchViewStateReducerTest {

    private val mapper = CharacterModelMapper()
    private val reducer = SearchViewStateReducer(mapper)

    @Test
    fun `check that emptySearchHistoryState is emitted when SearchHistoryResult is Empty`() {
        runBlockingTest {
            val initialState: SearchViewState = SearchViewState.init
            val viewState: SearchViewState = reducer.reduce(
                initialState,
                SearchViewResult.LoadedHistory(emptyList())
            )
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchHistoryState { success(emptyList()) }
                }
            )
        }
    }

    @Test
    fun `check that SearchHistoryLoadedState is emitted when SearchHistoryResult is Success`() {
        runBlockingTest {
            val initialState: SearchViewState = SearchViewState.init
            val list: List<Character> = DummyData.characterList
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchViewResult.LoadedHistory(list))
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchHistoryState { success(mapper.mapToModelList(list)) }
                }
            )
        }
    }

    @Test
    fun `check that SearchingState is emitted when SearchCharacterResult is Searching`() {
        runBlockingTest {
            val initialState: SearchViewState = SearchViewState.init
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Searching)
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchResultState { searching }
                }
            )
        }
    }

    @Test
    fun `check that SearchResultLoadedState is emitted when SearchCharacterResult is Success`() {
        runBlockingTest {
            val initialState: SearchViewState = SearchViewState.init
            val list: List<Character> = DummyData.characterList
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Success(list))
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchResultState { success(mapper.mapToModelList(list)) }
                }
            )
        }
    }

    @Test
    fun `check that SearchResultErrorState is emitted when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState: SearchViewState = SearchViewState.init
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Error(Throwable(ERROR_MSG)))
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchResultState { error(ERROR_MSG) }
                }
            )
        }
    }

    @Test
    fun `check that fall back error message is returned when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState: SearchViewState = SearchViewState.init
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Error(Throwable()))
            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    searchResultState { error("An error occurred") }
                }
            )
        }
    }
}
