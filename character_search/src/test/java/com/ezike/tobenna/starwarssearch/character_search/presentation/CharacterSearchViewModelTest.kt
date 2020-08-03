package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeCharacterRepository
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchCharacterViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchHistoryViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState.SearchCharacterViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewStateReducer
import com.ezike.tobenna.starwarssearch.domain.usecase.search.SearchCharacters
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.ClearSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.GetSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.SaveSearch
import com.ezike.tobenna.starwarssearch.testutils.ERROR_MSG
import com.ezike.tobenna.starwarssearch.testutils.FlowRecorder
import com.ezike.tobenna.starwarssearch.testutils.MainCoroutineRule
import com.ezike.tobenna.starwarssearch.testutils.ResponseType
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.testutils.containsElements
import com.ezike.tobenna.starwarssearch.testutils.recordWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule
import org.junit.Test

class CharacterSearchViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val characterModelMapper = CharacterModelMapper()
    private val stateRecorder: FlowRecorder<SearchViewState> = FlowRecorder(TestCoroutineScope())

    private val fakeSearchHistoryRepository = FakeSearchHistoryRepository()
    private val fakeCharacterRepository = FakeCharacterRepository()
    private val testPostExecutionThread = TestPostExecutionThread()

    private val viewModel: CharacterSearchViewModel by lazy {
        CharacterSearchViewModel(
            SearchViewStateMachine(
                SearchViewIntentProcessor(
                    SearchCharacters(fakeCharacterRepository, testPostExecutionThread),
                    SaveSearch(fakeSearchHistoryRepository),
                    GetSearchHistory(fakeSearchHistoryRepository, testPostExecutionThread),
                    ClearSearchHistory(fakeSearchHistoryRepository), characterModelMapper
                ),
                SearchViewStateReducer(characterModelMapper)
            )
        )
    }

    @Test
    fun `check that idle and empty search history viewState is first emitted`() {
        /**
         * Pause the dispatcher so that coroutines don't run yet.
         * This allows us capture the initial viewState emitted from [CharacterSearchViewModel.viewState].
         * That emission usually gets lost before we subscribe to the stream.
         */
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        // Resume the dispatcher and then run the coroutines
        mainCoroutineRule.resumeDispatcher()
        assertThat(stateRecorder.takeAll())
            .containsElements(SearchViewState.Idle, SearchHistoryViewState.SearchHistoryEmpty)
    }

    @Test
    fun `check that loaded search history viewState is emitted`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val character: CharacterModel = DummyData.characterModel
        viewModel.processIntent(flowOf(SearchCharacterViewIntent.SaveSearch(character)))
        viewModel.processIntent(flowOf(SearchHistoryViewIntent.LoadSearchHistory))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SearchViewState.Idle,
                SearchHistoryViewState.SearchHistoryEmpty,
                SearchHistoryViewState.SearchHistoryLoaded(listOf(character))
            )
    }

    @Test
    fun `check that emptySearchHistory viewState is emitted when searches are cleared`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val character: CharacterModel = DummyData.characterModel

        viewModel.processIntent(flowOf(SearchCharacterViewIntent.SaveSearch(character)))
        viewModel.processIntent(flowOf(SearchHistoryViewIntent.LoadSearchHistory))
        viewModel.processIntent(flowOf(SearchHistoryViewIntent.ClearSearchHistory))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SearchViewState.Idle,
                SearchHistoryViewState.SearchHistoryEmpty,
                SearchHistoryViewState.SearchHistoryLoaded(listOf(character)),
                SearchHistoryViewState.SearchHistoryEmpty
            )
    }

    @Test
    fun `check that searching and search loaded viewState is emitted`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        viewModel.processIntent(flowOf(SearchCharacterViewIntent.Search(DummyData.query)))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SearchViewState.Idle,
                SearchHistoryViewState.SearchHistoryEmpty,
                SearchCharacterViewState.Searching,
                SearchCharacterViewState.SearchResultLoaded(
                    characterModelMapper.mapToModelList(DummyData.characterList)
                )
            )
    }

    @Test
    fun `check that searching and Error viewState is emitted`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        // set the response to error
        fakeCharacterRepository.responseType = ResponseType.ERROR
        viewModel.processIntent(flowOf(SearchCharacterViewIntent.Search(DummyData.query)))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SearchViewState.Idle,
                SearchHistoryViewState.SearchHistoryEmpty,
                SearchCharacterViewState.Searching,
                SearchCharacterViewState.Error(ERROR_MSG)
            )
    }

    @Test
    fun `check that search result loaded viewState is emitted after error is resolved`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        // set the response to error
        fakeCharacterRepository.responseType = ResponseType.ERROR
        viewModel.processIntent(flowOf(SearchCharacterViewIntent.Search(DummyData.query)))
        fakeCharacterRepository.responseType = ResponseType.DATA
        viewModel.processIntent(flowOf(SearchCharacterViewIntent.Search(DummyData.query)))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SearchViewState.Idle,
                SearchHistoryViewState.SearchHistoryEmpty,
                SearchCharacterViewState.Searching,
                SearchCharacterViewState.Error(ERROR_MSG),
                SearchCharacterViewState.Searching,
                SearchCharacterViewState.SearchResultLoaded(
                    characterModelMapper.mapToModelList(DummyData.characterList)
                )
            )
    }

    @Test
    fun `check that empty searchHistory is emitted when search query is empty and no history is saved`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        // set the response to error
        viewModel.processIntent(flowOf(SearchCharacterViewIntent.Search("")))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SearchViewState.Idle,
                SearchHistoryViewState.SearchHistoryEmpty
            )
    }

    @Test
    fun `check that searchHistoryLoaded state is emitted when search query is empty and history is saved`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        // set the response to error
        val character: CharacterModel = DummyData.characterModel
        viewModel.processIntent(flowOf(SearchCharacterViewIntent.SaveSearch(character)))
        viewModel.processIntent(flowOf(SearchCharacterViewIntent.Search("")))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SearchViewState.Idle,
                SearchHistoryViewState.SearchHistoryEmpty,
                SearchHistoryViewState.SearchHistoryLoaded(listOf(character))
            )
    }

    @Test
    fun `check that empty searchHistory is emitted after result loaded when search query is empty and no history is saved`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        // set the response to error
        viewModel.processIntent(flowOf(SearchCharacterViewIntent.Search(DummyData.query)))
        viewModel.processIntent(flowOf(SearchCharacterViewIntent.Search("")))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SearchViewState.Idle,
                SearchHistoryViewState.SearchHistoryEmpty,
                SearchCharacterViewState.Searching,
                SearchCharacterViewState.SearchResultLoaded(
                    characterModelMapper.mapToModelList(DummyData.characterList)
                ), SearchHistoryViewState.SearchHistoryEmpty
            )
    }
}
