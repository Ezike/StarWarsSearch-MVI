package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewStateReducer
import com.ezike.tobenna.starwarssearch.domain.usecase.search.SearchCharacters
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.ClearSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.GetSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.SaveSearch
import com.ezike.tobenna.starwarssearch.testutils.FlowRecorder
import com.ezike.tobenna.starwarssearch.testutils.MainCoroutineRule
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule

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
}
