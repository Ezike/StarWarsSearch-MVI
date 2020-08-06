package com.ezike.tobenna.starwarssearch.remote.remote

import com.ezike.tobenna.starwarssearch.data.contract.remote.CharacterDetailRemote
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.remote.mapper.CharacterDetailRemoteMapper
import com.ezike.tobenna.starwarssearch.remote.mapper.FilmRemoteMapper
import com.ezike.tobenna.starwarssearch.remote.mapper.PlanetRemoteMapper
import com.ezike.tobenna.starwarssearch.remote.utils.CHARACTER_URL
import com.ezike.tobenna.starwarssearch.remote.utils.FILM_URL
import com.ezike.tobenna.starwarssearch.remote.utils.PLANET_URL
import com.ezike.tobenna.starwarssearch.remote.utils.RequestDispatcher
import com.ezike.tobenna.starwarssearch.remote.utils.SPECIES_URL
import com.ezike.tobenna.starwarssearch.remote.utils.SPECIES_URL_2
import com.ezike.tobenna.starwarssearch.remote.utils.makeTestApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class CharacterDetailRemoteImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var characterDetailRemote: CharacterDetailRemote
    private val characterDetailRemoteMapper = CharacterDetailRemoteMapper()
    private val planetRemoteMapper = PlanetRemoteMapper()
    private val filmRemoteMapper = FilmRemoteMapper()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = RequestDispatcher()
        mockWebServer.start()
        characterDetailRemote = CharacterDetailRemoteImpl(
            makeTestApiService(mockWebServer),
            characterDetailRemoteMapper,
            planetRemoteMapper,
            filmRemoteMapper
        )
    }

    @Test
    fun `check that fetchCharacter returns data`() = runBlocking {
        val character: CharacterDetailEntity = characterDetailRemote.fetchCharacter(CHARACTER_URL)
        assertThat(character.filmUrls).isNotEmpty()
        assertThat(character.speciesUrls).isEmpty()
        assertThat(character.planetUrl).isEqualTo("http://swapi.dev/api/planets/1/")
        assertThat(character.url).isEqualTo("http://swapi.dev/api/people/11/")
    }

    @Test
    fun `check that fetchPlanet returns planet info`() = runBlocking {
        val planet: PlanetEntity = characterDetailRemote.fetchPlanet(PLANET_URL)
        assertThat(planet.name).isEqualTo("Tatooine")
        assertThat(planet.population).isEqualTo("120000")
    }

    @Test
    fun `check that fetchSpecies returns species info`() = runBlocking {
        val species: List<SpecieEntity> = characterDetailRemote.fetchSpecies(listOf(SPECIES_URL))
        assertThat(species.first().name).isEqualTo("Neimodian")
        assertThat(species.first().language).isEqualTo("Neimoidia")
        assertThat(species.first().homeWorld).isEqualTo("Tatooine")
    }

    @Test
    fun `check that fetchSpecies returns species info 2`() = runBlocking {
        val species: List<SpecieEntity> =
            characterDetailRemote.fetchSpecies(listOf(SPECIES_URL, SPECIES_URL_2))
        assertThat(species.last().name).isEqualTo("Human")
        assertThat(species.last().language).isEqualTo("Galactic Basic")
        assertThat(species.last().homeWorld).isEqualTo("Kamino")
    }

    @Test
    fun `check that fetchSpecies returns nothing when urls is empty`() = runBlocking {
        val species: List<SpecieEntity> = characterDetailRemote.fetchSpecies(emptyList())
        assertThat(species).isEmpty()
    }

    @Test
    fun `check that fetchFilms returns movies`() = runBlocking {
        val films: List<FilmEntity> = characterDetailRemote.fetchFilms(listOf(FILM_URL))
        assertThat(films).isNotEmpty()
        assertThat(films.first().title).isEqualTo("A New Hope")
    }

    @Test
    fun `check that fetchFilms returns nothing when urls is empty`() = runBlocking {
        val films: List<FilmEntity> = characterDetailRemote.fetchFilms(emptyList())
        assertThat(films).isEmpty()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
