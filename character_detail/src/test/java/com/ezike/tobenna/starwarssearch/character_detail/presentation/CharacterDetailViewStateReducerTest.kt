package com.ezike.tobenna.starwarssearch.character_detail.presentation

import com.ezike.tobenna.starwarssearch.character_detail.data.DummyData
import com.ezike.tobenna.starwarssearch.character_detail.mapper.CharacterDetailModelMapper
import com.ezike.tobenna.starwarssearch.character_detail.mapper.FilmModelMapper
import com.ezike.tobenna.starwarssearch.character_detail.mapper.PlanetModelMapper
import com.ezike.tobenna.starwarssearch.character_detail.mapper.SpecieModelMapper
import com.ezike.tobenna.starwarssearch.character_detail.presentation.viewstate.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_detail.presentation.viewstate.CharacterDetailViewStateFactory
import com.ezike.tobenna.starwarssearch.character_detail.presentation.viewstate.translateTo
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.profile.ProfileViewStateFactory
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
    private val characterModelMapper: CharacterDetailModelMapper = CharacterDetailModelMapper()

    private val reducer = CharacterDetailViewStateReducer(
        planetModelMapper,
        specieModelMapper,
        filmModelMapper,
        characterModelMapper
    )

    @Test
    fun `check that CharacterDetailViewState is emitted when result is CharacterDetail`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState
            val character: Character = DummyData.character

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, CharacterDetailViewResult.CharacterDetail(character))

            val characterModel = characterModelMapper.mapToModel(character)

            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    profileState(
                        profileViewState = ProfileViewStateFactory.create(characterModel)
                    )
                }
            )
        }
    }

    @Test
    fun `check that RetryingViewState is emitted when result is Retrying`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, CharacterDetailViewResult.Retrying)

            assertThat(viewState).isEqualTo(initialState.translateTo { retryState })
        }
    }

    @Test
    fun `check that FetchDetailErrorViewState is emitted when result is FetchCharacterDetailError`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val error: SocketTimeoutException = DummyData.exception
            val characterName = DummyData.character.name

            val viewState: CharacterDetailViewState =
                reducer.reduce(
                    initialState,
                    CharacterDetailViewResult.FetchCharacterDetailError(characterName, error)
                )

            assertThat(viewState).isEqualTo(
                initialState.translateTo { errorState(characterName, error.errorMessage) }
            )
        }
    }

    @Test
    fun `check that PlanetDetailViewStateSuccess is emitted when result is PlanetDetailViewResultSuccess`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val planet: Planet = DummyData.planet

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, PlanetDetailViewResult.Success(planet))

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
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, PlanetDetailViewResult.Loading)

            assertThat(viewState).isEqualTo(initialState.translateTo { planetState { Loading } })
        }
    }

    @Test
    fun `check that PlanetDetailViewStateError is emitted when result is PlanetDetailViewResultError`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val error: SocketTimeoutException = DummyData.exception
            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, PlanetDetailViewResult.Error(error))

            assertThat(viewState).isEqualTo(initialState.translateTo { planetState { Error(error.errorMessage) } })
        }
    }

    @Test
    fun `check that FilmDetailViewStateSuccess is emitted when result is FilmDetailViewResultSuccess`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val films: List<Film> = DummyData.films

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, FilmDetailViewResult.Success(films))

            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    filmState { Success(filmModelMapper.mapToModelList(films)) }
                }
            )
        }
    }

    @Test
    fun `check that FilmDetailViewStateLoading is emitted when result is FilmDetailViewResultLoading`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, FilmDetailViewResult.Loading)

            assertThat(viewState).isEqualTo(initialState.translateTo { filmState { Loading } })
        }
    }

    @Test
    fun `check that FilmDetailViewStateLError is emitted when result is FilmDetailViewResultError`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val error: SocketTimeoutException = DummyData.exception
            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, FilmDetailViewResult.Error(error))

            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    filmState { Error(error.errorMessage) }
                }
            )
        }
    }

    @Test
    fun `check that SpecieDetailViewStateSuccess is emitted when result is SpecieDetailViewResultSuccess`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val species: List<Specie> = DummyData.species

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, SpecieDetailViewResult.Success(species))

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
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, SpecieDetailViewResult.Loading)

            assertThat(viewState).isEqualTo(initialState.translateTo { specieState { Loading } })
        }
    }

    @Test
    fun `check that SpecieDetailViewStateError is emitted when result is SpecieDetailViewResultError`() {
        runBlockingTest {
            val initialState: CharacterDetailViewState =
                CharacterDetailViewStateFactory.initialState

            val error: SocketTimeoutException = DummyData.exception
            val viewState: CharacterDetailViewState =
                reducer.reduce(initialState, SpecieDetailViewResult.Error(error))

            assertThat(viewState).isEqualTo(
                initialState.translateTo {
                    specieState { Error(error.errorMessage) }
                }
            )
        }
    }
}
