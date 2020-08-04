package com.ezike.tobenna.starwarssearch.character_search.presentation.mvi

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewResult.SearchHistoryResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState.Idle
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState.SearchCharacterViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewStateReducer
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
            val initialState: SearchViewState = Idle
            val viewState: SearchViewState = reducer.reduce(initialState, SearchHistoryResult.Empty)
            assertThat(viewState).isEqualTo(SearchHistoryViewState.SearchHistoryEmpty)
        }
    }

    @Test
    fun `check that SearchHistoryLoadedState is emitted when SearchHistoryResult is Success`() {
        runBlockingTest {
            val initialState: SearchViewState = Idle
            val list: List<Character> = DummyData.characterList
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchHistoryResult.Success(list))
            assertThat(viewState).isEqualTo(
                SearchHistoryViewState.SearchHistoryLoaded(mapper.mapToModelList(list))
            )
        }
    }

    @Test
    fun `check that SearchingState is emitted when SearchCharacterResult is Searching`() {
        runBlockingTest {
            val initialState: SearchViewState = Idle
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Searching)
            assertThat(viewState).isEqualTo(SearchCharacterViewState.Searching)
        }
    }

    @Test
    fun `check that SearchResultLoadedState is emitted when SearchCharacterResult is Success`() {
        runBlockingTest {
            val initialState: SearchViewState = Idle
            val list: List<Character> = DummyData.characterList
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Success(list))
            assertThat(viewState).isEqualTo(
                SearchCharacterViewState.SearchResultLoaded(mapper.mapToModelList(list))
            )
        }
    }

    @Test
    fun `check that SearchResultErrorState is emitted when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState: SearchViewState = Idle
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Error(Throwable(ERROR_MSG)))
            assertThat(viewState).isEqualTo(SearchCharacterViewState.Error(ERROR_MSG))
        }
    }

    @Test
    fun `check that fall back error message is returned when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState: SearchViewState = Idle
            val viewState: SearchViewState =
                reducer.reduce(initialState, SearchCharacterResult.Error(Throwable()))
            assertThat(viewState).isEqualTo(SearchCharacterViewState.Error("An error occurred"))
        }
    }
}
