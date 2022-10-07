package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.usecase.searchhistory

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.executor.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SaveSearchTest {

    private val searchHistoryRepository = FakeSearchHistoryRepository()
    private val saveSearch = SaveSearch(searchHistoryRepository, TestPostExecutionThread())

    @Test
    fun `check that saveSearch caches the search`() = runBlockingTest {
        val character: Character = DummyData.character
        saveSearch(character)
        val result: Character = searchHistoryRepository.getSearchHistory().first().first()
        assertThat(character).isEqualTo(result)
    }

    @Test
    fun `check that saveSearch replaces a character if it is already saved`() = runBlockingTest {
        val name = "Martin"
        val character: Character = DummyData.character
        val character2: Character = character.copy(name = name)
        saveSearch(character)
        saveSearch(character2)
        val result: Character = searchHistoryRepository.getSearchHistory().first().first()
        assertThat(character).isNotEqualTo(result)
        assertThat(character2.name).isEqualTo(name)
    }
}
