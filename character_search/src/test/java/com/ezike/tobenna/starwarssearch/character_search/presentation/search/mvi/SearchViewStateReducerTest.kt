package com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.domain.model.Character
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
            val initialState = SearchViewState()
            val viewState: SearchViewState = reducer.reduce(
                initialState,
                SearchViewResult.LoadedHistory(
                    emptyList()
                )
            )
            assertThat(viewState).isEqualTo(initialState.history { success(emptyList()) })
        }
    }

    @Test
    fun `check that SearchHistoryLoadedState is emitted when SearchHistoryResult is Success`() {
        runBlockingTest {
            val initialState = SearchViewState()
            val list: List<Character> = DummyData.characterList
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchViewResult.LoadedHistory(list))
            assertThat(viewState).isEqualTo(
                initialState.history { success(mapper.mapToModelList(list)) }
            )
        }
    }

    @Test
    fun `check that SearchingState is emitted when SearchCharacterResult is Searching`() {
        runBlockingTest {
            val initialState = SearchViewState()
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Searching)
            assertThat(viewState).isEqualTo(initialState.searchResult { searching })
        }
    }

    @Test
    fun `check that SearchResultLoadedState is emitted when SearchCharacterResult is Success`() {
        runBlockingTest {
            val initialState = SearchViewState()
            val list: List<Character> = DummyData.characterList
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Success(list))
            assertThat(viewState).isEqualTo(
                initialState.searchResult { success(mapper.mapToModelList(list)) }
            )
        }
    }

    @Test
    fun `check that SearchResultErrorState is emitted when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState = SearchViewState()
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Error(Throwable(ERROR_MSG)))
            assertThat(viewState).isEqualTo(initialState.searchResult { error(ERROR_MSG) })
        }
    }

    @Test
    fun `check that fall back error message is returned when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState = SearchViewState()
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Error(Throwable()))
            assertThat(viewState).isEqualTo(initialState.searchResult { error("An error occurred") })
        }
    }
}
