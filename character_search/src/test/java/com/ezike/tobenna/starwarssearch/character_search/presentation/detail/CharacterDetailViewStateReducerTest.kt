package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Film
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Planet
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Specie
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.net.SocketTimeoutException

// TODO: redo this test
class CharacterDetailViewStateReducerTest {

    private val planetModelMapper: PlanetModelMapper = PlanetModelMapper()
    private val specieModelMapper: SpecieModelMapper = SpecieModelMapper()
    private val filmModelMapper: FilmModelMapper = FilmModelMapper()
    private val characterModelMapper: CharacterModelMapper = CharacterModelMapper()

    private val reducer =
        com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewStateReducer(
            planetModelMapper,
            specieModelMapper,
            filmModelMapper,
            characterModelMapper
        )

    @Test
    fun `check that CharacterDetailViewState is emitted when result is CharacterDetail`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init
            val character: Character = DummyData.character

            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.CharacterDetail(character))

            assertThat(viewState).isEqualTo(
                initialState.translateTo { initialState(characterModelMapper.mapToModel(character)) }
            )
        }
    }

    @Test
    fun `check that RetryingViewState is emitted when result is Retrying`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.Retrying)

            assertThat(viewState).isEqualTo(initialState.translateTo { retryState })
        }
    }

    @Test
    fun `check that FetchDetailErrorViewState is emitted when result is FetchCharacterDetailError`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val error: SocketTimeoutException = DummyData.exception
            val characterName = DummyData.character.name

            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(
                    initialState,
                    com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewResult.FetchCharacterDetailError(characterName, error)
                )

            assertThat(viewState).isEqualTo(
                initialState.translateTo { errorState(characterName, error.errorMessage) }
            )
        }
    }

    @Test
    fun `check that PlanetDetailViewStateSuccess is emitted when result is PlanetDetailViewResultSuccess`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val planet: Planet = DummyData.planet

            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Success(planet))

            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    planetState { Success(planetModelMapper.mapToModel(planet)) }
                }
            )
        }
    }

    @Test
    fun `check that PlanetDetailViewStateLoading is emitted when result is PlanetDetailViewResultLoading`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Loading)

            assertThat(viewState).isEqualTo(initialState.translateTo { planetState { Loading } })
        }
    }

    @Test
    fun `check that PlanetDetailViewStateError is emitted when result is PlanetDetailViewResultError`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val error: SocketTimeoutException = DummyData.exception
            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.PlanetDetailViewResult.Error(error))

            assertThat(viewState).isEqualTo(initialState.translateTo { planetState { Error(error.errorMessage) } })
        }
    }

    @Test
    fun `check that FilmDetailViewStateSuccess is emitted when result is FilmDetailViewResultSuccess`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val films: List<Film> = DummyData.films

            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Success(films))

            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    filmState { success(filmModelMapper.mapToModelList(films)) }
                }
            )
        }
    }

    @Test
    fun `check that FilmDetailViewStateLoading is emitted when result is FilmDetailViewResultLoading`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Loading)

            assertThat(viewState).isEqualTo(initialState.translateTo { filmState { loading } })
        }
    }

    @Test
    fun `check that FilmDetailViewStateLError is emitted when result is FilmDetailViewResultError`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val error: SocketTimeoutException = DummyData.exception
            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.FilmDetailViewResult.Error(error))

            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    filmState { error(error.errorMessage) }
                }
            )
        }
    }

    @Test
    fun `check that SpecieDetailViewStateSuccess is emitted when result is SpecieDetailViewResultSuccess`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val species: List<Specie> = DummyData.species

            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Success(species))

            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    specieState { DataLoaded(specieModelMapper.mapToModelList(species)) }
                }
            )
        }
    }

    @Test
    fun `check that SpecieDetailViewStateLoading is emitted when result is SpecieDetailViewResultLoading`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Loading)

            assertThat(viewState).isEqualTo(initialState.translateTo { specieState { Loading } })
        }
    }

    @Test
    fun `check that SpecieDetailViewStateError is emitted when result is SpecieDetailViewResultError`() {
        runBlockingTest {
            val initialState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState = com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState.init

            val error: SocketTimeoutException = DummyData.exception
            val viewState: com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.CharacterDetailViewState =
                reducer.reduce(initialState, com.ezike.tobenna.starwarssearch.character_detail.characterDetail.detail.SpecieDetailViewResult.Error(error))

            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    specieState { Error(error.errorMessage) }
                }
            )
        }
    }
}
