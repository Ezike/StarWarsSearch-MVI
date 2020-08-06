package com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.FilmModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.PlanetModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.SpecieModelMapper
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.domain.model.Specie
import com.google.common.truth.Truth.assertThat
import java.net.SocketTimeoutException
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class CharacterDetailViewStateReducerTest {

    private val planetModelMapper: PlanetModelMapper = PlanetModelMapper()
    private val specieModelMapper: SpecieModelMapper = SpecieModelMapper()
    private val filmModelMapper: FilmModelMapper = FilmModelMapper()
    private val characterModelMapper: CharacterModelMapper = CharacterModelMapper()

    private val reducer = CharacterDetailViewStateReducer(
        planetModelMapper,
        specieModelMapper,
        filmModelMapper,
        characterModelMapper
    )

    @Test
    fun `check that idleViewState is emitted when result is Idle`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle
            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, CharacterDetailViewResult.Idle)
            assertThat(viewState).isEqualTo(initialState)
        }
    }

    @Test
    fun `check that CharacterDetailViewState is emitted when result is CharacterDetail`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle
            val character: Character = DummyData.character

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, CharacterDetailViewResult.CharacterDetail(character))

            assertThat(viewState).isEqualTo(
                CharacterDetailViewState
                    .ProfileLoaded(characterModelMapper.mapToModel(character))
            )
        }
    }

    @Test
    fun `check that RetryingViewState is emitted when result is Retrying`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, CharacterDetailViewResult.Retrying)

            assertThat(viewState).isEqualTo(CharacterDetailViewState.Retrying)
        }
    }

    @Test
    fun `check that FetchDetailErrorViewState is emitted when result is FetchCharacterDetailError`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val error: SocketTimeoutException = DummyData.exception
            val viewState: CharacterDetailViewState =
                reducer.reduce(
                    initialState,
                    CharacterDetailViewResult.FetchCharacterDetailError(error)
                )

            assertThat(viewState).isEqualTo(
                CharacterDetailViewState.FetchDetailError(error.errorMessage)
            )
        }
    }

    @Test
    fun `check that PlanetDetailViewStateSuccess is emitted when result is PlanetDetailViewResultSuccess`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val planet: Planet = DummyData.planet

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, PlanetDetailViewResult.Success(planet))

            assertThat(viewState).isEqualTo(
                PlanetDetailViewState.Success(planetModelMapper.mapToModel(planet))
            )
        }
    }

    @Test
    fun `check that PlanetDetailViewStateLoading is emitted when result is PlanetDetailViewResultLoading`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, PlanetDetailViewResult.Loading)

            assertThat(viewState).isEqualTo(PlanetDetailViewState.Loading)
        }
    }

    @Test
    fun `check that PlanetDetailViewStateError is emitted when result is PlanetDetailViewResultError`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val error: SocketTimeoutException = DummyData.exception
            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, PlanetDetailViewResult.Error(error))

            assertThat(viewState).isEqualTo(PlanetDetailViewState.Error(error.errorMessage))
        }
    }

    @Test
    fun `check that FilmDetailViewStateSuccess is emitted when result is FilmDetailViewResultSuccess`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val films: List<Film> = DummyData.films

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, FilmDetailViewResult.Success(films))

            assertThat(viewState).isEqualTo(
                FilmDetailViewState.Success(filmModelMapper.mapToModelList(films))
            )
        }
    }

    @Test
    fun `check that FilmDetailViewStateLoading is emitted when result is FilmDetailViewResultLoading`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, FilmDetailViewResult.Loading)

            assertThat(viewState).isEqualTo(FilmDetailViewState.Loading)
        }
    }

    @Test
    fun `check that FilmDetailViewStateLError is emitted when result is FilmDetailViewResultError`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val error: SocketTimeoutException = DummyData.exception
            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, FilmDetailViewResult.Error(error))

            assertThat(viewState).isEqualTo(FilmDetailViewState.Error(error.errorMessage))
        }
    }

    @Test
    fun `check that SpecieDetailViewStateSuccess is emitted when result is SpecieDetailViewResultSuccess`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val species: List<Specie> = DummyData.species

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, SpecieDetailViewResult.Success(species))

            assertThat(viewState).isEqualTo(
                SpecieDetailViewState.Success(specieModelMapper.mapToModelList(species))
            )
        }
    }

    @Test
    fun `check that SpecieDetailViewStateLoading is emitted when result is SpecieDetailViewResultLoading`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, SpecieDetailViewResult.Loading)

            assertThat(viewState).isEqualTo(SpecieDetailViewState.Loading)
        }
    }

    @Test
    fun `check that SpecieDetailViewStateError is emitted when result is SpecieDetailViewResultError`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState = CharacterDetailViewState.Idle

            val error: SocketTimeoutException = DummyData.exception
            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, SpecieDetailViewResult.Error(error))

            assertThat(viewState).isEqualTo(SpecieDetailViewState.Error(error.errorMessage))
        }
    }
}
