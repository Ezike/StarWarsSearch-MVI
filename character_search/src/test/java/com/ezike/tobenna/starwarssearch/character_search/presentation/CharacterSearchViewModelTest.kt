package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.CharacterSearchViewModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewStateReducer
import com.ezike.tobenna.starwarssearch.character_search.ui.search.LoadSearchHistory
import com.ezike.tobenna.starwarssearch.character_search.views.search.ClearSearchHistoryIntent
import com.ezike.tobenna.starwarssearch.character_search.views.search.SaveSearchIntent
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchIntent
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
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule
import org.junit.Test

class CharacterSearchViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val characterModelMapper = CharacterModelMapper()
    private val stateRecorder: FlowRecorder<SearchViewState> = FlowRecorder(TestCoroutineScope())

    private val fakeSearchHistoryRepository = FakeSearchHistoryRepository()
    private val fakeCharacterRepository = FakeSearchRepository()
    private val testPostExecutionThread = TestPostExecutionThread()

    private val viewModel: CharacterSearchViewModel by lazy {
        CharacterSearchViewModel(
            SearchViewStateMachine(
                SearchViewIntentProcessor(
                    SearchCharacters(fakeCharacterRepository, testPostExecutionThread),
                    SaveSearch(fakeSearchHistoryRepository, testPostExecutionThread),
                    GetSearchHistory(fakeSearchHistoryRepository, testPostExecutionThread),
                    ClearSearchHistory(fakeSearchHistoryRepository, testPostExecutionThread),
                    characterModelMapper
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
        val state = SearchViewState()
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        // Resume the dispatcher and then run the coroutines
        mainCoroutineRule.resumeDispatcher()
        assertThat(stateRecorder.takeAll())
            .containsElements(state, state.history { success(emptyList()) })
    }

    @Test
    fun `check that loaded search history viewState is emitted`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val state = SearchViewState()
        val character: CharacterModel = DummyData.characterModel
        viewModel.processIntent(SaveSearchIntent(character))
        viewModel.processIntent(LoadSearchHistory)

        assertThat(stateRecorder.takeAll())
            .containsElements(
                state,
                state.history { success(emptyList()) },
                state.history { success(listOf(character)) }
            )
    }

    @Test
    fun `check that emptySearchHistory viewState is emitted when searches are cleared`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val state = SearchViewState()
        val character: CharacterModel = DummyData.characterModel

        viewModel.processIntent(SaveSearchIntent(character))
        viewModel.processIntent(LoadSearchHistory)
        viewModel.processIntent(ClearSearchHistoryIntent)

        assertThat(stateRecorder.takeAll())
            .containsElements(
                state,
                state.history { success(emptyList()) },
                state.history { success(listOf(character)) },
                state.history { success(emptyList()) }
            )
    }

    @Test
    fun `check that searching and search loaded viewState is emitted`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val state = SearchViewState()

        viewModel.processIntent(SearchIntent(DummyData.query))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                state,
                state.history { success(emptyList()) },
                state.searchResult { searching },
                state.searchResult {
                    success(characterModelMapper.mapToModelList(DummyData.characterList))
                }
            )
    }

    @Test
    fun `check that searching and Error viewState is emitted`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val state = SearchViewState()
        // set the response to error
        fakeCharacterRepository.responseType = ResponseType.ERROR
        viewModel.processIntent(SearchIntent(DummyData.query))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                state,
                state.history { success(emptyList()) },
                state.searchResult { searching },
                state.searchResult { error(ERROR_MSG) }
            )
    }

    @Test
    fun `check that search result loaded viewState is emitted after error is resolved`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val state = SearchViewState()
        // set the response to error
        fakeCharacterRepository.responseType = ResponseType.ERROR
        viewModel.processIntent(SearchIntent(DummyData.query))
        fakeCharacterRepository.responseType = ResponseType.DATA
        viewModel.processIntent(SearchIntent(DummyData.query))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                state,
                state.history { success(emptyList()) },
                state.searchResult { searching },
                state.searchResult { error(ERROR_MSG) },
                state.searchResult { searching },
                state.searchResult {
                    success(characterModelMapper.mapToModelList(DummyData.characterList))
                }
            )
    }

    @Test
    fun `check that empty searchHistory is emitted when search query is empty and no history is saved`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val state = SearchViewState()
        // set the response to error
        viewModel.processIntent(SearchIntent(""))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                state,
                state.history { success(emptyList()) }
            )
    }

    @Test
    fun `check that searchHistoryLoaded state is emitted when search query is empty and history is saved`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val state = SearchViewState()
        // set the response to error
        val character: CharacterModel = DummyData.characterModel
        viewModel.processIntent(SaveSearchIntent(character))
        viewModel.processIntent(SearchIntent(""))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                state,
                state.history { success(emptyList()) },
                state.history { success(listOf(character)) }
            )
    }

    @Test
    fun `check that empty searchHistory is emitted after result loaded when search query is empty and no history is saved`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val state = SearchViewState()
        // set the response to error
        viewModel.processIntent(SearchIntent(DummyData.query))
        viewModel.processIntent(SearchIntent(""))

        assertThat(stateRecorder.takeAll())
            .containsElements(
                state,
                state.history { success(emptyList()) },
                state.searchResult { searching },
                state.searchResult {
                    success(characterModelMapper.mapToModelList(DummyData.characterList))
                },
                state.history { success(emptyList()) }
            )
    }
}
