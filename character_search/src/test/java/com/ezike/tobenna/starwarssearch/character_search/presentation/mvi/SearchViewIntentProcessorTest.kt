package com.ezike.tobenna.starwarssearch.character_search.presentation.mvi

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchCharacterViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchHistoryViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewResult.SearchCharacterResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewResult.SearchHistoryResult
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.usecase.search.SearchCharacters
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.ClearSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.GetSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.SaveSearch
import com.ezike.tobenna.starwarssearch.testutils.ERROR_MSG
import com.ezike.tobenna.starwarssearch.testutils.FlowRecorder
import com.ezike.tobenna.starwarssearch.testutils.ResponseType
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.testutils.containsElements
import com.ezike.tobenna.starwarssearch.testutils.recordWith
import com.google.common.truth.Truth.assertThat
import java.net.SocketTimeoutException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

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
        recordSearchHistoryResult(SearchHistoryViewIntent.LoadSearchHistory)
        assertThat(resultRecorder.takeAll()).containsElements(SearchHistoryResult.Success(list))
    }

    @Test
    fun `check that LoadSearchHistoryIntent returns EmptyResult`() = runBlockingTest {
        recordSearchHistoryResult(SearchHistoryViewIntent.LoadSearchHistory)
        assertThat(resultRecorder.takeAll())
            .containsElements(SearchHistoryResult.Empty)
    }

    @Test
    fun `check that ClearSearchHistoryIntent returns EmptyResult`() = runBlockingTest {
        val list: List<Character> = DummyData.characterList
        fakeSearchHistoryRepository.saveSearch(list.first())
        recordSearchHistoryResult(SearchHistoryViewIntent.ClearSearchHistory)
        assertThat(resultRecorder.takeAll())
            .containsElements(SearchHistoryResult.Empty)
    }

    @Test
    fun `check that SearchCharacterIntent returns SuccessResult`() {
        recordSearchResult(SearchCharacterViewIntent.Search(DummyData.query), ResponseType.DATA)
        assertThat(resultRecorder.takeAll())
            .containsElements(
                SearchCharacterResult.Searching,
                SearchCharacterResult.Success(DummyData.characterList)
            )
    }

    @Test
    fun `SearchCharacterIntent returns emptySearchHistoryResult when query is empty and no search is saved`() {
        searchViewIntentProcessor.intentToResult(SearchCharacterViewIntent.Search(""))
            .recordWith(resultRecorder)
        assertThat(resultRecorder.takeAll())
            .containsElements(SearchHistoryResult.Empty)
    }

    @Test
    fun `SearchCharacterIntent returns loadedSearchHistoryResult when query is empty and search is saved`() =
        runBlockingTest {
            val list: List<Character> = DummyData.characterList
            fakeSearchHistoryRepository.saveSearch(list.first())
            searchViewIntentProcessor.intentToResult(SearchCharacterViewIntent.Search(""))
                .recordWith(resultRecorder)
            assertThat(resultRecorder.takeAll()).containsElements(SearchHistoryResult.Success(list))
        }

    @Test
    fun `check that SearchCharacterIntent returns ErrorResult`() {
        recordSearchResult(SearchCharacterViewIntent.Search(DummyData.query), ResponseType.ERROR)
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
            SearchCharacterViewIntent.SaveSearch(characterModelMapper.mapToModel(list.first()))
        ).collect()
        recordSearchHistoryResult(SearchHistoryViewIntent.LoadSearchHistory)
        assertThat(resultRecorder.takeAll()).containsElements(SearchHistoryResult.Success(list))
    }

    private fun recordSearchResult(intent: SearchViewIntent, type: ResponseType) {
        fakeCharacterRepository.responseType = type
        searchViewIntentProcessor.intentToResult(intent).recordWith(resultRecorder)
    }

    private fun recordSearchHistoryResult(intent: SearchViewIntent) {
        searchViewIntentProcessor.intentToResult(intent).recordWith(resultRecorder)
    }
}
