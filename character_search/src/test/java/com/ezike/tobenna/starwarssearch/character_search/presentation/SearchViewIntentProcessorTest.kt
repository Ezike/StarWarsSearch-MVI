package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeCharacterRepository
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewIntentProcessor
import com.ezike.tobenna.starwarssearch.domain.usecase.search.SearchCharacters
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.ClearSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.GetSearchHistory
import com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory.SaveSearch
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
import org.junit.Test

class SearchViewIntentProcessorTest {

    private val searchViewIntentProcessor =
        SearchViewIntentProcessor(
            SearchCharacters(FakeCharacterRepository(), TestPostExecutionThread()),
            SaveSearch(FakeSearchHistoryRepository()),
            GetSearchHistory(FakeSearchHistoryRepository(), TestPostExecutionThread()),
            ClearSearchHistory(FakeSearchHistoryRepository()),
            CharacterModelMapper()
        )

    @Test
    fun intentToResult() {
    }
}
