package com.ezike.tobenna.starwarssearch.charactersearch.presentation

import com.ezike.tobenna.starwarssearch.charactersearch.data.DummyData
import com.ezike.tobenna.starwarssearch.charactersearch.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchScreenResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.viewstate.SearchScreenState
import com.ezike.tobenna.starwarssearch.charactersearch.ui.views.history.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.charactersearch.ui.views.result.SearchResultViewState
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
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
                SearchScreenState.HistoryView(
                    SearchHistoryViewState.DataLoaded(emptyList())
                )
            )
        }
    }

    @Test
    fun `check that SearchHistoryLoadedState is emitted when SearchHistoryResult is Success`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val list: List<Character> = DummyData.characterList
            val viewState: SearchScreenState = reducer.reduce(
                initialState,
                SearchScreenResult.LoadedHistory(list)
            )
            assertThat(viewState).isEqualTo(
                SearchScreenState.HistoryView(
                    SearchHistoryViewState.DataLoaded(mapper.mapToModelList(list))
                )
            )
        }
    }

    @Test
    fun `check that SearchingState is emitted when SearchCharacterResult is Searching`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val viewState: SearchScreenState = reducer.reduce(
                initialState,
                SearchCharacterResult.Searching
            )
            assertThat(viewState).isEqualTo(
                SearchScreenState.ResultView(
                    SearchResultViewState.Searching(emptyList())
                )
            )
        }
    }

    @Test
    fun `check that SearchResultLoadedState is emitted when SearchCharacterResult is Success`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val list: List<Character> = DummyData.characterList
            val viewState: SearchScreenState = reducer.reduce(
                initialState,
                SearchCharacterResult.LoadedSearchResult(list)
            )
            assertThat(viewState).isEqualTo(
                SearchScreenState.ResultView(
                    SearchResultViewState.DataLoaded(mapper.mapToModelList(list))
                )
            )
        }
    }

    @Test
    fun `check that SearchResultErrorState is emitted when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val viewState: SearchScreenState = reducer.reduce(
                initialState,
                SearchCharacterResult.SearchError(Throwable(ERROR_MSG))
            )
            assertThat(viewState).isEqualTo(
                SearchScreenState.ResultView(
                    SearchResultViewState.Error(ERROR_MSG)
                )
            )
        }
    }

    @Test
    fun `check that fall back error message is returned when SearchCharacterResult is Error`() {
        runBlockingTest {
            val initialState: SearchScreenState = SearchScreenState.Initial
            val viewState: SearchScreenState = reducer.reduce(
                initialState,
                SearchCharacterResult.SearchError(Throwable())
            )
            assertThat(viewState).isEqualTo(
                SearchScreenState.ResultView(
                    SearchResultViewState.Error("An error occurred")
                )
            )
        }
    }
}
