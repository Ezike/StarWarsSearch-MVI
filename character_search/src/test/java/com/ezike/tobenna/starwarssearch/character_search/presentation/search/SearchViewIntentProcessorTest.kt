package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.character_search.ui.search.LoadSearchHistory
import com.ezike.tobenna.starwarssearch.character_search.views.search.ClearSearchHistoryIntent
import com.ezike.tobenna.starwarssearch.character_search.views.search.SaveSearchIntent
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchIntent
import com.ezike.tobenna.starwarssearch.character_search.views.search.UpdateHistoryIntent
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.usecase.search.SearchCharacters
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.ClearSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.GetSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.SaveSearch
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.testutils.ERROR_MSG
import com.ezike.tobenna.starwarssearch.testutils.FlowRecorder
import com.ezike.tobenna.starwarssearch.testutils.ResponseType
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.testutils.containsElements
import com.ezike.tobenna.starwarssearch.testutils.recordWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.net.SocketTimeoutException

class SearchViewIntentProcessorTest {

    private val fakeCharacterRepository = FakeSearchRepository()

    private val fakeSearchHistoryRepository = FakeSearchHistoryRepository()

    private val characterModelMapper = CharacterModelMapper()

    private val testPostExecutionThread = TestPostExecutionThread()

    private val searchViewIntentProcessor =
        SearchViewIntentProcessor(
            SearchCharacters(fakeCharacterRepository, testPostExecutionThread),
            SaveSearch(fakeSearchHistoryRepository, testPostExecutionThread),
            GetSearchHistory(fakeSearchHistoryRepository, testPostExecutionThread),
            ClearSearchHistory(fakeSearchHistoryRepository, testPostExecutionThread),
            characterModelMapper
        )

    private val resultRecorder: FlowRecorder<SearchViewResult> = FlowRecorder(TestCoroutineScope())

    @Test
    fun `check that LoadSearchHistoryIntent returns SuccessResult`() = runBlockingTest {
        val list: List<Character> = DummyData.characterList
        fakeSearchHistoryRepository.saveSearch(list.first())
        recordSearchHistoryResult(LoadSearchHistory)
        assertThat(resultRecorder.takeAll()).containsElements(SearchViewResult.LoadedHistory(list))
    }

    @Test
    fun `check that LoadSearchHistoryIntent returns EmptyResult`() = runBlockingTest {
        recordSearchHistoryResult(LoadSearchHistory)
        assertThat(resultRecorder.takeAll())
            .containsElements(SearchViewResult.LoadedHistory(emptyList()))
    }

    @Test
    fun `check that ClearSearchHistoryIntent returns EmptyResult`() = runBlockingTest {
        val list: List<Character> = DummyData.characterList
        fakeSearchHistoryRepository.saveSearch(list.first())
        recordSearchHistoryResult(ClearSearchHistoryIntent)
        assertThat(resultRecorder.takeAll())
            .containsElements(SearchViewResult.LoadedHistory(emptyList()))
    }

    @Test
    fun `check that UpdateHistoryIntent returns History in order`() = runBlockingTest {
        val first: Character = DummyData.character
        val second: Character = DummyData.character.copy(name = "amig")
        val third: Character = DummyData.character.copy(birthYear = "1997")
        fakeSearchHistoryRepository.saveSearch(first)
        fakeSearchHistoryRepository.saveSearch(second)
        fakeSearchHistoryRepository.saveSearch(third)
        recordSearchHistoryResult(UpdateHistoryIntent(characterModelMapper.mapToModel(second)))
        assertThat(resultRecorder.takeAll())
            .containsElements(SearchViewResult.LoadedHistory(listOf(second)))
    }

    @Test
    fun `check that SearchCharacterIntent returns SuccessResult`() {
        recordSearchResult(SearchIntent(DummyData.query), ResponseType.DATA)
        assertThat(resultRecorder.takeAll())
            .containsElements(
                SearchCharacterResult.Searching,
                SearchCharacterResult.Success(DummyData.characterList)
            )
    }

    @Test
    fun `SearchCharacterIntent returns emptySearchHistoryResult when query is empty and no search is saved`() {
        searchViewIntentProcessor.intentToResult(SearchIntent(""))
            .recordWith(resultRecorder)
        assertThat(resultRecorder.takeAll())
            .containsElements(SearchViewResult.LoadedHistory(emptyList()))
    }

    @Test
    fun `SearchCharacterIntent returns loadedSearchHistoryResult when query is empty and search is saved`() =
        runBlockingTest {
            val list: List<Character> = DummyData.characterList
            fakeSearchHistoryRepository.saveSearch(list.first())
            searchViewIntentProcessor.intentToResult(SearchIntent(""))
                .recordWith(resultRecorder)
            assertThat(resultRecorder.takeAll()).containsElements(
                SearchViewResult.LoadedHistory(
                    list
                )
            )
        }

    @Test
    fun `check that SearchCharacterIntent returns ErrorResult`() {
        recordSearchResult(SearchIntent(DummyData.query), ResponseType.ERROR)
        val results: List<SearchViewResult> = resultRecorder.takeAll()
        assertThat(results.map { it.javaClass })
            .containsElements(
                SearchCharacterResult.Searching::class.java,
                SearchCharacterResult.Error::class.java
            )
        val errorResult: SearchCharacterResult.Error = results.last() as SearchCharacterResult.Error
        assertThat(errorResult.throwable).isInstanceOf(SocketTimeoutException::class.java)
        assertThat(errorResult.throwable.message).isEqualTo(ERROR_MSG)
    }

    @Test
    fun `check that SaveSearchIntent saves current character`() = runBlockingTest {
        val list: List<Character> = DummyData.characterList
        searchViewIntentProcessor.intentToResult(
            SaveSearchIntent(characterModelMapper.mapToModel(list.first()))
        ).collect()
        recordSearchHistoryResult(LoadSearchHistory)
        assertThat(resultRecorder.takeAll()).containsElements(SearchViewResult.LoadedHistory(list))
    }

    private fun recordSearchResult(intent: ViewIntent, type: ResponseType) {
        fakeCharacterRepository.responseType = type
        searchViewIntentProcessor.intentToResult(intent).recordWith(resultRecorder)
    }

    private fun recordSearchHistoryResult(intent: ViewIntent) {
        searchViewIntentProcessor.intentToResult(intent).recordWith(resultRecorder)
    }
}
