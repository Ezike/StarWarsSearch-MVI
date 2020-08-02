package com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory

import com.ezike.tobenna.starwarssearch.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.domain.executor.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class GetSearchHistoryTest {

    private val searchHistoryRepository = FakeSearchHistoryRepository()
    private val getSearchHistory =
        GetSearchHistory(searchHistoryRepository, TestPostExecutionThread())

    @Test
    fun `check that getSearchHistory returns list of recent searches`() = runBlockingTest {
        val character: Character = DummyData.character
        val character2: Character = character.copy(url = "https://swapi.dev/people/11/")
        val character3: Character = character.copy(url = "https://swapi.dev/people/19/")

        searchHistoryRepository.saveSearch(character)
        searchHistoryRepository.saveSearch(character2)
        searchHistoryRepository.saveSearch(character3)

        val allItems: List<Character> = listOf(character3, character2, character)
        val result: List<Character> = getSearchHistory().first()
        assertThat(allItems).containsExactlyElementsIn(result).inOrder()
    }
}
