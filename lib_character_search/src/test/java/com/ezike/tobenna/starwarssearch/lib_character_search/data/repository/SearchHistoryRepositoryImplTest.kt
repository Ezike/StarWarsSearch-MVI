package com.ezike.tobenna.starwarssearch.lib_character_search.data.repository

import com.ezike.tobenna.starwarssearch.lib_character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.data.fakes.FakeSearchHistoryCache
import com.ezike.tobenna.starwarssearch.lib_character_search.data.mapper.CharacterEntityMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SearchHistoryRepositoryImplTest {

    private val characterEntityMapper = CharacterEntityMapper()

    private val searchHistoryRepository =
        SearchHistoryRepositoryImpl(FakeSearchHistoryCache(), characterEntityMapper)

    @Test
    fun saveSearch() = runBlockingTest {
        val character: Character = DummyData.character
        searchHistoryRepository.saveSearch(character)
        val result: Character = searchHistoryRepository.getSearchHistory().first().first()
        assertThat(character).isEqualTo(result)
    }

    @Test
    fun `check that saveSearch does not save duplicates`() = runBlockingTest {
        val name = "Martin"
        val character: Character = DummyData.character
        val character2: Character = character.copy(name = name)
        searchHistoryRepository.saveSearch(character)
        searchHistoryRepository.saveSearch(character2)
        val result: Character = searchHistoryRepository.getSearchHistory().first().first()
        assertThat(character).isNotEqualTo(result)
        assertThat(character2.name).isEqualTo(name)
    }

    @Test
    fun `check that getSearchHistory returns all saved items`() = runBlockingTest {
        val character: Character = DummyData.character
        val character1: Character = character.copy(url = "https://swap.dev/people/2/")
        val character2: Character = character.copy(url = "https://swap.dev/people/21/")

        searchHistoryRepository.saveSearch(character)
        searchHistoryRepository.saveSearch(character1)
        searchHistoryRepository.saveSearch(character2)

        val allItems: List<Character> = listOf(character2, character1, character)
        val result: List<Character> = searchHistoryRepository.getSearchHistory().first()
        assertThat(allItems).containsExactlyElementsIn(result).inOrder()
    }

    @Test
    fun `check that clearHistory clears the cache`() = runBlockingTest {
        val character: CharacterEntity = DummyData.characterEntity
        searchHistoryRepository.saveSearch(characterEntityMapper.mapFromEntity(character))
        searchHistoryRepository.clearSearchHistory()
        val result: List<Character> = searchHistoryRepository.getSearchHistory().first()
        assertThat(result).isEmpty()
    }
}
