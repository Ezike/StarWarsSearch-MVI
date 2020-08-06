package com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchFilms
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchPlanet
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchSpecies
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.GetCharacterDetail
import com.ezike.tobenna.starwarssearch.testutils.ERROR_MSG
import com.ezike.tobenna.starwarssearch.testutils.FlowRecorder
import com.ezike.tobenna.starwarssearch.testutils.ResponseType
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.testutils.containsElements
import com.ezike.tobenna.starwarssearch.testutils.recordWith
import com.google.common.truth.Truth.assertThat
import java.net.SocketTimeoutException
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class CharacterDetailViewIntentProcessorTest {

    private val repository = FakeCharacterDetailRepository()
    private val characterModelMapper = CharacterModelMapper()
    private val testPostExecutionThread = TestPostExecutionThread()

    private val processor =
        CharacterDetailViewIntentProcessor(
            FetchPlanet(repository, testPostExecutionThread),
            FetchSpecies(repository, testPostExecutionThread),
            FetchFilms(repository, testPostExecutionThread),
            GetCharacterDetail(repository, testPostExecutionThread),
            characterModelMapper
        )

    private val resultRecorder: FlowRecorder<CharacterDetailViewResult> =
        FlowRecorder(TestCoroutineScope())

    @Test
    fun `check that IdleIntent returns IdleResult`() = runBlockingTest {
        processor.intentToResult(CharacterDetailViewIntent.Idle).recordWith(resultRecorder)
        assertThat(resultRecorder.takeAll())
            .containsElements(CharacterDetailViewResult.Idle)
    }

    @Test
    fun `check that LoadCharacterDetailIntent returns character details`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            CharacterDetailViewIntent.LoadCharacterDetail(character)
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll()).containsElements(
            CharacterDetailViewResult.CharacterDetail(
                characterModelMapper.mapToDomain(character)
            ), FilmDetailViewResult.Loading, FilmDetailViewResult.Success(DummyData.films),
            PlanetDetailViewResult.Loading, PlanetDetailViewResult.Success(DummyData.planet),
            SpecieDetailViewResult.Loading, SpecieDetailViewResult.Success(DummyData.species)
        )
    }

    @Test
    fun `check that LoadCharacterDetailIntent returns errorResult if getCharacterDetail fails`() =
        runBlockingTest {
            repository.characterResponseType = ResponseType.ERROR
            val character: CharacterModel = DummyData.characterModel
            processor.intentToResult(
                CharacterDetailViewIntent
                    .LoadCharacterDetail(character)
            ).recordWith(resultRecorder)

            val results: List<CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    CharacterDetailViewResult.CharacterDetail::class.java,
                    CharacterDetailViewResult.FetchCharacterDetailError::class.java
                )

            val errorResult: CharacterDetailViewResult.FetchCharacterDetailError =
                results.last() as CharacterDetailViewResult.FetchCharacterDetailError
            assertThat(errorResult.error).isInstanceOf(SocketTimeoutException::class.java)
            assertThat(errorResult.error.message).isEqualTo(ERROR_MSG)
        }

    @Test
    fun `check that LoadCharacterDetailIntent returns errorResult when getFilms fails`() =
        runBlockingTest {
            repository.filmResponseType = ResponseType.ERROR

            val character: CharacterModel = DummyData.characterModel
            processor.intentToResult(
                CharacterDetailViewIntent.LoadCharacterDetail(character)
            ).recordWith(resultRecorder)

            val results: List<CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    CharacterDetailViewResult.CharacterDetail::class.java,
                    FilmDetailViewResult.Loading::class.java,
                    FilmDetailViewResult.Error::class.java,
                    PlanetDetailViewResult.Loading::class.java,
                    PlanetDetailViewResult.Success::class.java,
                    SpecieDetailViewResult.Loading::class.java,
                    SpecieDetailViewResult.Success::class.java
                )
        }

    @Test
    fun `check that LoadCharacterDetailIntent returns errorResult when getPlanet fails`() =
        runBlockingTest {
            repository.planetResponseType = ResponseType.ERROR

            val character: CharacterModel = DummyData.characterModel
            processor.intentToResult(
                CharacterDetailViewIntent.LoadCharacterDetail(character)
            ).recordWith(resultRecorder)

            val results: List<CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    CharacterDetailViewResult.CharacterDetail::class.java,
                    FilmDetailViewResult.Loading::class.java,
                    FilmDetailViewResult.Success::class.java,
                    PlanetDetailViewResult.Loading::class.java,
                    PlanetDetailViewResult.Error::class.java,
                    SpecieDetailViewResult.Loading::class.java,
                    SpecieDetailViewResult.Success::class.java
                )
        }

    @Test
    fun `check that LoadCharacterDetailIntent returns errorResult when getSpecies fails`() =
        runBlockingTest {
            repository.specieResponseType = ResponseType.ERROR

            val character: CharacterModel = DummyData.characterModel
            processor.intentToResult(
                CharacterDetailViewIntent.LoadCharacterDetail(character)
            ).recordWith(resultRecorder)

            val results: List<CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    CharacterDetailViewResult.CharacterDetail::class.java,
                    FilmDetailViewResult.Loading::class.java,
                    FilmDetailViewResult.Success::class.java,
                    PlanetDetailViewResult.Loading::class.java,
                    PlanetDetailViewResult.Success::class.java,
                    SpecieDetailViewResult.Loading::class.java,
                    SpecieDetailViewResult.Error::class.java
                )
        }

    @Test
    fun `check that LoadCharacterDetailIntent returns errorResult when all calls fail`() =
        runBlockingTest {
            repository.specieResponseType = ResponseType.ERROR
            repository.filmResponseType = ResponseType.ERROR
            repository.planetResponseType = ResponseType.ERROR

            val character: CharacterModel = DummyData.characterModel
            processor.intentToResult(
                CharacterDetailViewIntent.LoadCharacterDetail(character)
            ).recordWith(resultRecorder)

            val results: List<CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    CharacterDetailViewResult.CharacterDetail::class.java,
                    FilmDetailViewResult.Loading::class.java,
                    FilmDetailViewResult.Error::class.java,
                    PlanetDetailViewResult.Loading::class.java,
                    PlanetDetailViewResult.Error::class.java,
                    SpecieDetailViewResult.Loading::class.java,
                    SpecieDetailViewResult.Error::class.java
                )
        }

    @Test
    fun `check that RetryFetchPlanet returns data`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            CharacterDetailViewIntent.RetryFetchPlanet(character.url)
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll())
            .containsElements(
                PlanetDetailViewResult.Loading,
                PlanetDetailViewResult.Success(DummyData.planet)
            )
    }

    @Test
    fun `check that RetryFetchPlanet returns error`() = runBlockingTest {
        repository.planetResponseType = ResponseType.ERROR
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            CharacterDetailViewIntent.RetryFetchPlanet(character.url)
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll().map { it.javaClass })
            .containsElements(
                PlanetDetailViewResult.Loading::class.java,
                PlanetDetailViewResult.Error::class.java
            )
    }

    @Test
    fun `check that RetryFetchSpecie returns data`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            CharacterDetailViewIntent.RetryFetchSpecie(character.url)
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll())
            .containsElements(
                SpecieDetailViewResult.Loading,
                SpecieDetailViewResult.Success(DummyData.species)
            )
    }

    @Test
    fun `check that RetryFetchSpecie returns error`() = runBlockingTest {
        repository.specieResponseType = ResponseType.ERROR
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            CharacterDetailViewIntent.RetryFetchSpecie(character.url)
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll().map { it.javaClass })
            .containsElements(
                SpecieDetailViewResult.Loading::class.java,
                SpecieDetailViewResult.Error::class.java
            )
    }

    @Test
    fun `check that RetryFetchFilm returns data`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            CharacterDetailViewIntent.RetryFetchFilm(character.url)
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll())
            .containsElements(
                FilmDetailViewResult.Loading,
                FilmDetailViewResult.Success(DummyData.films)
            )
    }

    @Test
    fun `check that RetryFetchFilm returns error`() = runBlockingTest {
        repository.filmResponseType = ResponseType.ERROR
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            CharacterDetailViewIntent.RetryFetchFilm(character.url)
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll().map { it.javaClass })
            .containsElements(
                FilmDetailViewResult.Loading::class.java,
                FilmDetailViewResult.Error::class.java
            )
    }

    @Test
    fun `check that RetryFetchCharacterDetails returns data`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            CharacterDetailViewIntent.RetryFetchCharacterDetails(character)
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll()).containsElements(
            CharacterDetailViewResult.CharacterDetail(
                characterModelMapper.mapToDomain(character)
            ), FilmDetailViewResult.Loading, FilmDetailViewResult.Success(DummyData.films),
            PlanetDetailViewResult.Loading, PlanetDetailViewResult.Success(DummyData.planet),
            SpecieDetailViewResult.Loading, SpecieDetailViewResult.Success(DummyData.species)
        )
    }

    @Test
    fun `check that RetryFetchCharacterDetails returns error`() = runBlockingTest {
        repository.characterResponseType = ResponseType.ERROR
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            CharacterDetailViewIntent.RetryFetchCharacterDetails(character)
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll().map { it.javaClass })
            .containsElements(
                CharacterDetailViewResult.CharacterDetail::class.java,
                CharacterDetailViewResult.FetchCharacterDetailError::class.java
            )
    }
}
