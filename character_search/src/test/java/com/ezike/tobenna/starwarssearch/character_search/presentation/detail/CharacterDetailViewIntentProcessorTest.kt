package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import com.ezike.tobenna.starwarssearch.character_detail.characterDetail.LoadCharacterDetailIntent
import com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchCharacterDetailsIntent
import com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchFilmIntent
import com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchPlanetIntent
import com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchSpecieIntent
import com.ezike.tobenna.starwarssearch.character_search.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.detail.FetchFilms
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.detail.FetchPlanet
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.detail.FetchSpecies
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.detail.GetCharacterDetail
import com.ezike.tobenna.starwarssearch.testutils.ERROR_MSG
import com.ezike.tobenna.starwarssearch.testutils.FlowRecorder
import com.ezike.tobenna.starwarssearch.testutils.ResponseType
import com.ezike.tobenna.starwarssearch.testutils.containsElements
import com.ezike.tobenna.starwarssearch.testutils.recordWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.net.SocketTimeoutException

class CharacterDetailViewIntentProcessorTest {

    private val repository = FakeCharacterDetailRepository()
    private val characterModelMapper = CharacterModelMapper()
    private val testPostExecutionThread = TestPostExecutionThread()

    private val processor =
        com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewIntentProcessor(
            FetchPlanet(repository, testPostExecutionThread),
            FetchSpecies(repository, testPostExecutionThread),
            FetchFilms(repository, testPostExecutionThread),
            GetCharacterDetail(repository, testPostExecutionThread),
            characterModelMapper
        )

    private val resultRecorder: FlowRecorder<com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult> =
        FlowRecorder(TestCoroutineScope())

    @Test
    fun `check that LoadCharacterDetailIntent returns character details`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.LoadCharacterDetailIntent(
                character
            )
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll()).containsElements(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.CharacterDetail(
                characterModelMapper.mapToDomain(character)
            ),
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Loading,
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Success(DummyData.films),
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Loading,
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Success(DummyData.planet),
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Loading,
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Success(DummyData.species)
        )
    }

    @Test
    fun `check that LoadCharacterDetailIntent returns errorResult if getCharacterDetail fails`() =
        runBlockingTest {
            repository.characterResponseType = ResponseType.ERROR
            val character: CharacterModel = DummyData.characterModel
            processor.intentToResult(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.LoadCharacterDetailIntent(
                    character
                )
            ).recordWith(resultRecorder)

            val results: List<com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.CharacterDetail::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.FetchCharacterDetailError::class.java
                )

            val errorResult: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.FetchCharacterDetailError =
                results.last() as com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.FetchCharacterDetailError
            assertThat(errorResult.error).isInstanceOf(SocketTimeoutException::class.java)
            assertThat(errorResult.error.message).isEqualTo(ERROR_MSG)
        }

    @Test
    fun `check that LoadCharacterDetailIntent returns errorResult when getFilms fails`() =
        runBlockingTest {
            repository.filmResponseType = ResponseType.ERROR

            val character: CharacterModel = DummyData.characterModel
            processor.intentToResult(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.LoadCharacterDetailIntent(
                    character
                )
            ).recordWith(resultRecorder)

            val results: List<com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.CharacterDetail::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Error::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Success::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Success::class.java
                )
        }

    @Test
    fun `check that LoadCharacterDetailIntent returns errorResult when getPlanet fails`() =
        runBlockingTest {
            repository.planetResponseType = ResponseType.ERROR

            val character: CharacterModel = DummyData.characterModel
            processor.intentToResult(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.LoadCharacterDetailIntent(
                    character
                )
            ).recordWith(resultRecorder)

            val results: List<com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.CharacterDetail::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Success::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Error::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Success::class.java
                )
        }

    @Test
    fun `check that LoadCharacterDetailIntent returns errorResult when getSpecies fails`() =
        runBlockingTest {
            repository.specieResponseType = ResponseType.ERROR

            val character: CharacterModel = DummyData.characterModel
            processor.intentToResult(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.LoadCharacterDetailIntent(
                    character
                )
            ).recordWith(resultRecorder)

            val results: List<com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.CharacterDetail::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Success::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Success::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Error::class.java
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
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.LoadCharacterDetailIntent(
                    character
                )
            ).recordWith(resultRecorder)

            val results: List<com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult> = resultRecorder.takeAll()
            assertThat(results.map { it.javaClass })
                .containsElements(
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.CharacterDetail::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Error::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Error::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Loading::class.java,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Error::class.java
                )
        }

    @Test
    fun `check that RetryFetchPlanet returns data`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchPlanetIntent(
                character.url
            )
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll())
            .containsElements(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Loading,
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Success(DummyData.planet)
            )
    }

    @Test
    fun `check that RetryFetchPlanet returns error`() = runBlockingTest {
        repository.planetResponseType = ResponseType.ERROR
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchPlanetIntent(
                character.url
            )
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll().map { it.javaClass })
            .containsElements(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Loading::class.java,
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Error::class.java
            )
    }

    @Test
    fun `check that RetryFetchSpecie returns data`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchSpecieIntent(
                character.url
            )
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll())
            .containsElements(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Loading,
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Success(DummyData.species)
            )
    }

    @Test
    fun `check that RetryFetchSpecie returns error`() = runBlockingTest {
        repository.specieResponseType = ResponseType.ERROR
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchSpecieIntent(
                character.url
            )
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll().map { it.javaClass })
            .containsElements(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Loading::class.java,
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Error::class.java
            )
    }

    @Test
    fun `check that RetryFetchFilm returns data`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchFilmIntent(
                character.url
            )
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll())
            .containsElements(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Loading,
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Success(DummyData.films)
            )
    }

    @Test
    fun `check that RetryFetchFilm returns error`() = runBlockingTest {
        repository.filmResponseType = ResponseType.ERROR
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchFilmIntent(
                character.url
            )
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll().map { it.javaClass })
            .containsElements(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Loading::class.java,
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Error::class.java
            )
    }

    @Test
    fun `check that RetryFetchCharacterDetails returns data`() = runBlockingTest {
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchCharacterDetailsIntent(
                character
            )
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll()).containsElements(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.Retrying,
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Loading,
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Success(DummyData.films),
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Loading,
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Success(DummyData.planet),
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Loading,
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Success(DummyData.species)
        )
    }

    @Test
    fun `check that RetryFetchCharacterDetails returns error`() = runBlockingTest {
        repository.characterResponseType = ResponseType.ERROR
        val character: CharacterModel = DummyData.characterModel

        processor.intentToResult(
            com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.RetryFetchCharacterDetailsIntent(
                character
            )
        ).recordWith(resultRecorder)

        assertThat(resultRecorder.takeAll().map { it.javaClass })
            .containsElements(
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.Retrying::class.java,
                com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.FetchCharacterDetailError::class.java
            )
    }
}
