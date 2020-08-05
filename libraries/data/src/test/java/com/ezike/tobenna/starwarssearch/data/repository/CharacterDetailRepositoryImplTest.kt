package com.ezike.tobenna.starwarssearch.data.repository

import com.ezike.tobenna.starwarssearch.data.DummyData
import com.ezike.tobenna.starwarssearch.data.fakes.FakeCharacterDetailCache
import com.ezike.tobenna.starwarssearch.data.fakes.FakeCharacterDetailRemote
import com.ezike.tobenna.starwarssearch.data.mapper.CharacterDetailEntityMapper
import com.ezike.tobenna.starwarssearch.data.mapper.FilmEntityMapper
import com.ezike.tobenna.starwarssearch.data.mapper.PlanetEntityMapper
import com.ezike.tobenna.starwarssearch.data.mapper.SpeciesEntityMapper
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.domain.model.Specie
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class CharacterDetailRepositoryImplTest {

    private val fakeCharacterDetailCache = FakeCharacterDetailCache()
    private val characterDetailEntityMapper = CharacterDetailEntityMapper()
    private val planetEntityMapper = PlanetEntityMapper()
    private val filmEntityMapper = FilmEntityMapper()
    private val speciesEntityMapper = SpeciesEntityMapper()

    private val repository = CharacterDetailRepositoryImpl(
        FakeCharacterDetailRemote(),
        fakeCharacterDetailCache,
        characterDetailEntityMapper,
        planetEntityMapper,
        filmEntityMapper,
        speciesEntityMapper
    )

    @Test
    fun `check that getCharacterDetail returns data from remote when cache is empty`() =
        runBlockingTest {
            val characterDetailEntity: CharacterDetailEntity = DummyData.characterDetailEntity
            val character: Flow<CharacterDetail> =
                repository.getCharacterDetail(characterDetailEntity.url)
            assertThat(characterDetailEntity).isEqualTo(
                characterDetailEntityMapper.mapToEntity(character.first())
            )
        }

    @Test
    fun `check that getCharacterDetail returns data from cache`() = runBlockingTest {
        val characterDetailEntity1: CharacterDetailEntity = DummyData.characterDetailEntity
        val characterDetailEntity2: CharacterDetailEntity =
            characterDetailEntity1.copy(filmUrls = emptyList(), url = "https://google.com")
        fakeCharacterDetailCache.saveCharacter(characterDetailEntity2)
        val character: CharacterDetail =
            repository.getCharacterDetail(characterDetailEntity2.url).first()
        assertThat(characterDetailEntity2).isEqualTo(
            characterDetailEntityMapper.mapToEntity(
                character
            )
        )
        assertThat(characterDetailEntity1).isNotEqualTo(
            characterDetailEntityMapper.mapToEntity(
                character
            )
        )
    }

    @Test
    fun `check that fetchPlanet returns planet data`() = runBlockingTest {
        val planet: PlanetEntity = DummyData.planetEntity
        val result: Planet =
            repository.fetchPlanet(DummyData.characterDetailEntity.planetUrl).first()
        assertThat(planet).isEqualTo(planetEntityMapper.mapToEntity(result))
    }

    @Test
    fun `check that fetchSpecies returns species`() = runBlockingTest {
        val specie: SpecieEntity = DummyData.specieEntity
        val result: List<Specie> =
            repository.fetchSpecies(DummyData.characterDetailEntity.speciesUrls).first()
        assertThat(specie).isEqualTo(speciesEntityMapper.mapToEntity(result.first()))
    }

    @Test
    fun `check that fetchFilms returns films`() = runBlockingTest {
        val films: FilmEntity = DummyData.filmEntity
        val result: List<Film> =
            repository.fetchFilms(DummyData.characterDetailEntity.filmUrls).first()
        assertThat(films).isEqualTo(filmEntityMapper.mapToEntity(result.first()))
    }
}
