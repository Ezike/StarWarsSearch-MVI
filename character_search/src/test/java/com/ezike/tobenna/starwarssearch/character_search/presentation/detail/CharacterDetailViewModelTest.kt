package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.FilmModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.PlanetModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.SpecieModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewStateReducer
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.FilmDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.PlanetDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.SpecieDetailViewState
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchFilms
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchPlanet
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchSpecies
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.GetCharacterDetail
import com.ezike.tobenna.starwarssearch.testutils.FlowRecorder
import com.ezike.tobenna.starwarssearch.testutils.MainCoroutineRule
import com.ezike.tobenna.starwarssearch.testutils.ResponseType
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.testutils.recordWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule
import org.junit.Test

class CharacterDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val planetModelMapper = PlanetModelMapper()
    private val characterModelMapper = CharacterModelMapper()
    private val specieModelMapper = SpecieModelMapper()
    private val filmModelMapper = FilmModelMapper()

    private val stateRecorder: FlowRecorder<CharacterDetailViewState> =
        FlowRecorder(TestCoroutineScope())

    private val repository = FakeCharacterDetailRepository()
    private val testPostExecutionThread = TestPostExecutionThread()

    private val viewModel: CharacterDetailViewModel by lazy {
        CharacterDetailViewModel(
            CharacterDetailViewStateMachine(
                CharacterDetailViewIntentProcessor(
                    FetchPlanet(repository, testPostExecutionThread),
                    FetchSpecies(repository, testPostExecutionThread),
                    FetchFilms(repository, testPostExecutionThread),
                    GetCharacterDetail(repository, testPostExecutionThread),
                    characterModelMapper
                ),
                CharacterDetailViewStateReducer(
                    planetModelMapper,
                    specieModelMapper,
                    filmModelMapper,
                    characterModelMapper
                )
            )
        )
    }

    @Test
    fun `check that all viewState is emitted when LoadCharacterDetail intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val character: CharacterModel = DummyData.characterModel

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.LoadCharacterDetail(character))
        )

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    CharacterDetailViewState.Idle,
                    PlanetDetailViewState.Loading,
                    FilmDetailViewState.Loading,
                    SpecieDetailViewState.Loading,
                    CharacterDetailViewState.ProfileLoaded(character),
                    PlanetDetailViewState.Success(DummyData.planetModel),
                    FilmDetailViewState.Success(listOf(DummyData.filmModel)),
                    SpecieDetailViewState.Success(listOf(DummyData.specieModel))
                )
            )
    }

    @Test
    fun `check that Retrying viewState is emitted when RetryFetchCharacterDetails intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val character: CharacterModel = DummyData.characterModel

        repository.run {
            filmResponseType = ResponseType.ERROR
            planetResponseType = ResponseType.ERROR
            specieResponseType = ResponseType.ERROR
        }
        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchCharacterDetails(character))
        )

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    CharacterDetailViewState.Idle,
                    PlanetDetailViewState.Loading,
                    FilmDetailViewState.Loading,
                    SpecieDetailViewState.Loading,
                    CharacterDetailViewState.Retrying,
                    PlanetDetailViewState.Error(DummyData.exception.errorMessage),
                    FilmDetailViewState.Error(DummyData.exception.errorMessage),
                    SpecieDetailViewState.Error(DummyData.exception.errorMessage)
                )
            )
    }

    @Test
    fun `check that FetchCharacterDetailError viewState is emitted when LoadCharacterDetail intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        repository.characterResponseType = ResponseType.ERROR

        val character: CharacterModel = DummyData.characterModel

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.LoadCharacterDetail(character))
        )

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    CharacterDetailViewState.Idle,
                    PlanetDetailViewState.Loading,
                    FilmDetailViewState.Loading,
                    SpecieDetailViewState.Loading,
                    CharacterDetailViewState.ProfileLoaded(character),
                    CharacterDetailViewState.FetchDetailError(DummyData.exception.errorMessage)
                )
            )
    }

    @Test
    fun `check that FilmDetailViewState Success viewState is emitted when RetryFetchFilms intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)

        mainCoroutineRule.resumeDispatcher()

        repository.filmResponseType = ResponseType.ERROR

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchFilm(DummyData.characterModel.url))
        )

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    CharacterDetailViewState.Idle,
                    PlanetDetailViewState.Loading,
                    FilmDetailViewState.Loading,
                    SpecieDetailViewState.Loading,
                    FilmDetailViewState.Error(DummyData.exception.errorMessage)
                )
            )
    }

    @Test
    fun `check that PlanetDetailViewState Success viewState is emitted when RetryFetchPlanet intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        repository.planetResponseType = ResponseType.ERROR

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchPlanet(DummyData.characterModel.url))
        )

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    CharacterDetailViewState.Idle,
                    PlanetDetailViewState.Loading,
                    FilmDetailViewState.Loading,
                    SpecieDetailViewState.Loading,
                    PlanetDetailViewState.Error(DummyData.exception.errorMessage)
                )
            )
    }

    @Test
    fun `check that SpecieDetailViewState Success viewState is emitted when RetryFetchSpecies intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        repository.specieResponseType = ResponseType.ERROR

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchSpecie(DummyData.characterModel.url))
        )

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    CharacterDetailViewState.Idle,
                    PlanetDetailViewState.Loading,
                    FilmDetailViewState.Loading,
                    SpecieDetailViewState.Loading,
                    SpecieDetailViewState.Error(DummyData.exception.errorMessage)
                )
            )
    }
}
