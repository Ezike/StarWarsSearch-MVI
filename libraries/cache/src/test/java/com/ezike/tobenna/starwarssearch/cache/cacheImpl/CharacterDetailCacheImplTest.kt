package com.ezike.tobenna.starwarssearch.cache.cacheImpl

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ezike.tobenna.starwarssearch.cache.entity.DummyData
import com.ezike.tobenna.starwarssearch.cache.mapper.CharacterDetailCacheMapper
import com.ezike.tobenna.starwarssearch.cache.room.StarWarsDatabase
import com.ezike.tobenna.starwarssearch.data.contract.cache.CharacterDetailCache
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDetailCacheImplTest {

    private lateinit var cache: CharacterDetailCache
    private lateinit var starWarsDatabase: StarWarsDatabase

    @Before
    fun setup() {
        starWarsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StarWarsDatabase::class.java
        ).allowMainThreadQueries().build()

        cache = CharacterDetailCacheImpl(
            starWarsDatabase.characterDetailDao,
            CharacterDetailCacheMapper()
        )
    }

    @Test
    fun `check that saveCharacter inserts data into database`() = runBlocking {
        val character: CharacterDetailEntity = DummyData.characterDetailEntity

        cache.saveCharacter(character)

        val result: CharacterDetailEntity? = cache.fetchCharacter(character.url)
        assertThat(result).isNotNull()
    }

    @Test
    fun `check that fetchCharacter returns data`() = runBlocking {
        val character: CharacterDetailEntity = DummyData.characterDetailEntity

        cache.saveCharacter(character)

        val result: CharacterDetailEntity? = cache.fetchCharacter(character.url)
        assertThat(result).isNotNull()
        assertThat(result?.filmUrls).isEqualTo(character.filmUrls)
        assertThat(result?.planetUrl).isEqualTo(character.planetUrl)
        assertThat(result?.speciesUrls).isEqualTo(character.speciesUrls)
        assertThat(result?.url).isEqualTo(character.url)
    }

    @Test
    fun `check that fetchCharacter returns null data if database is empty`() = runBlocking {
        val character: CharacterDetailEntity = DummyData.characterDetailEntity
        val result: CharacterDetailEntity? = cache.fetchCharacter(character.url)
        assertThat(result).isNull()
    }

    @After
    fun tearDown() {
        starWarsDatabase.close()
    }
}
