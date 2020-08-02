package com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory

import com.ezike.tobenna.starwarssearch.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.domain.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ClearSearchHistoryTest {

    private val searchHistoryRepository = FakeSearchHistoryRepository()
    private val clearSearchHistory = ClearSearchHistory(searchHistoryRepository)

    @Test
    fun `check that clearSearchHistory removes cached data`() = runBlockingTest {
        val character: Character = DummyData.character
        searchHistoryRepository.saveSearch(character)
        clearSearchHistory()
        val result: List<Character> = searchHistoryRepository.getSearchHistory().first()
        assertThat(result).isEmpty()
    }
}
