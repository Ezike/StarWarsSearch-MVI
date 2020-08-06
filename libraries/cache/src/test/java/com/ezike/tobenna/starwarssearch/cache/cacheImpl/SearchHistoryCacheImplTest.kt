package com.ezike.tobenna.starwarssearch.cache.cacheImpl

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ezike.tobenna.starwarssearch.cache.entity.DummyData
import com.ezike.tobenna.starwarssearch.cache.mapper.CharacterCacheModelMapper
import com.ezike.tobenna.starwarssearch.cache.room.StarWarsDatabase
import com.ezike.tobenna.starwarssearch.data.contract.cache.SearchHistoryCache
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchHistoryCacheImplTest {

    private lateinit var searchHistoryCache: SearchHistoryCache
    private lateinit var starWarsDatabase: StarWarsDatabase

    @Before
    fun setup() {
        starWarsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StarWarsDatabase::class.java
        ).allowMainThreadQueries().build()

        searchHistoryCache = SearchHistoryCacheImpl(
            starWarsDatabase.searchHistoryDao,
            starWarsDatabase.characterDetailDao,
            CharacterCacheModelMapper()
        )
    }

    @Test
    fun `check that saveSearch inserts data into database`() = runBlocking {
        val character: CharacterEntity = DummyData.entity

        searchHistoryCache.saveSearch(character)

        val result: CharacterEntity = searchHistoryCache.getSearchHistory().first()
        assertThat(character.name).isEqualTo(result.name)
        assertThat(character.height).isEqualTo(result.height)
        assertThat(character.birthYear).isEqualTo(result.birthYear)
        assertThat(character.url).isEqualTo(result.url)
    }

    @Test
    fun `check that getSearchHistory returns the last saved search first`() = runBlocking {
        val character: CharacterEntity = DummyData.entity
        val character2: CharacterEntity = DummyData.entity.copy(url = "https://swapi.dev/1/")
        val character3: CharacterEntity = DummyData.entity.copy(url = "https://swapi.dev/4/")

        searchHistoryCache.saveSearch(character)
        searchHistoryCache.saveSearch(character2)
        searchHistoryCache.saveSearch(character3)

        val result: CharacterEntity = searchHistoryCache.getSearchHistory().first()
        assertThat(result).isEqualTo(character3)
    }

    @Test
    fun `check that saveSearch replaces already saved item`() = runBlocking {
        val character: CharacterEntity = DummyData.entity
        val name = "Daniel Lyssi"
        val character1: CharacterEntity = DummyData.entity.copy(name = name)

        searchHistoryCache.saveSearch(character)
        searchHistoryCache.saveSearch(character1)

        val result: CharacterEntity =
            searchHistoryCache.getSearchHistory().first()
        assertThat(result).isNotEqualTo(character)
        assertThat(result.name).isEqualTo(name)
    }

    @Test
    fun `check that getSearchHistory returns data in descending order`() = runBlocking {
        val character: CharacterEntity = DummyData.entity
        val character2: CharacterEntity = DummyData.entity.copy(url = "https://swapi.dev/1/")
        val character3: CharacterEntity = DummyData.entity.copy(url = "https://swapi.dev/4/")

        searchHistoryCache.saveSearch(character)
        searchHistoryCache.saveSearch(character2)
        searchHistoryCache.saveSearch(character3)

        val allItems: List<CharacterEntity> = listOf(character3, character2, character)
        val result: List<CharacterEntity> = searchHistoryCache.getSearchHistory()
        assertThat(allItems).containsExactlyElementsIn(result).inOrder()
    }

    @Test
    fun `check that clearSearchHistory clears all saved searches`() = runBlocking {
        val character: CharacterEntity = DummyData.entity
        val character3: CharacterEntity = DummyData.entity.copy(url = "https://swapi.dev/4/")

        searchHistoryCache.saveSearch(character)
        searchHistoryCache.saveSearch(character3)

        searchHistoryCache.clearSearchHistory()

        val result: List<CharacterEntity> = searchHistoryCache.getSearchHistory()
        assertThat(result).isEmpty()
    }

    @After
    fun tearDown() {
        starWarsDatabase.close()
    }
}
