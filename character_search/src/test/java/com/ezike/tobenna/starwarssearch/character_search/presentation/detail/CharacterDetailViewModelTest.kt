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
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.CharacterSearchViewModel
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchFilms
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchPlanet
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchSpecies
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.GetCharacterDetail
import com.ezike.tobenna.starwarssearch.testutils.FlowRecorder
import com.ezike.tobenna.starwarssearch.testutils.MainCoroutineRule
import com.ezike.tobenna.starwarssearch.testutils.ResponseType
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.testutils.containsElements
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
    fun `check that idle viewState is first emitted`() {
        /**
         * Pause the dispatcher so that coroutines don't run yet.
         * This allows us capture the initial viewState emitted from [CharacterSearchViewModel.viewState].
         * That emission usually gets lost before we subscribe to the stream.
         */
        mainCoroutineRule.pauseDispatcher()
        viewModel.detailViewState.recordWith(stateRecorder)
        // Resume the dispatcher and then run the coroutines
        mainCoroutineRule.resumeDispatcher()

        assertThat(stateRecorder.takeAll())
            .containsElements(CharacterDetailViewState.Idle)
    }

    @Test
    fun `check that ProfileLoaded viewState is emitted when LoadCharacterDetail intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.detailViewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val character: CharacterModel = DummyData.characterModel

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.LoadCharacterDetail(character))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                CharacterDetailViewState.Idle,
                CharacterDetailViewState.ProfileLoaded(character)
            )
    }

    @Test
    fun `check that FetchCharacterDetailError viewState is emitted when LoadCharacterDetail intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.detailViewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        repository.characterResponseType = ResponseType.ERROR

        val character: CharacterModel = DummyData.characterModel

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.LoadCharacterDetail(character))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                CharacterDetailViewState.Idle,
                CharacterDetailViewState.ProfileLoaded(character),
                CharacterDetailViewState.FetchDetailError(DummyData.exception.errorMessage)
            )
    }

    @Test
    fun `check that FilmDetailViewState Success viewState is emitted when LoadCharacterDetail intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.filmViewState.recordWith(stateRecorder)

        mainCoroutineRule.resumeDispatcher()

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.LoadCharacterDetail(DummyData.characterModel))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                FilmDetailViewState.Loading,
                FilmDetailViewState.Success(listOf(DummyData.filmModel))
            )
    }

    @Test
    fun `check that FilmDetailViewState Success viewState is emitted when RetryFetchFilms intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.filmViewState.recordWith(stateRecorder)

        mainCoroutineRule.resumeDispatcher()

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchFilm(DummyData.characterModel.url))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                FilmDetailViewState.Loading,
                FilmDetailViewState.Success(listOf(DummyData.filmModel))
            )
    }

    @Test
    fun `check that FilmDetailViewState error viewState is emitted when RetryFetchFilms intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.filmViewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        repository.filmResponseType = ResponseType.ERROR

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchFilm(DummyData.characterModel.url))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                FilmDetailViewState.Loading,
                FilmDetailViewState.Error(DummyData.exception.errorMessage)
            )
    }

    @Test
    fun `check that PlanetDetailViewState Success viewState is emitted when LoadCharacterDetail intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.planetViewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.LoadCharacterDetail(DummyData.characterModel))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                PlanetDetailViewState.Loading,
                PlanetDetailViewState.Success(DummyData.planetModel)
            )
    }

    @Test
    fun `check that PlanetDetailViewState Success viewState is emitted when RetryFetchPlanet intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.planetViewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchPlanet(DummyData.characterModel.url))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                PlanetDetailViewState.Loading,
                PlanetDetailViewState.Success(DummyData.planetModel)
            )
    }

    @Test
    fun `check that PlanetDetailViewState Success viewState is emitted when RetryFetchPlanet intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.planetViewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        repository.planetResponseType = ResponseType.ERROR

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchPlanet(DummyData.characterModel.url))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                PlanetDetailViewState.Loading,
                PlanetDetailViewState.Error(DummyData.exception.errorMessage)
            )
    }

    @Test
    fun `check that SpecieDetailViewState Success viewState is emitted when LoadCharacterDetail intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.speciesViewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.LoadCharacterDetail(DummyData.characterModel))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SpecieDetailViewState.Loading,
                SpecieDetailViewState.Success(listOf(DummyData.specieModel))
            )
    }

    @Test
    fun `check that SpecieDetailViewState Success viewState is emitted when RetryFetchSpecies intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.speciesViewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchSpecie(DummyData.characterModel.url))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SpecieDetailViewState.Loading,
                SpecieDetailViewState.Success(listOf(DummyData.specieModel))
            )
    }

    @Test
    fun `check that SpecieDetailViewState Success viewState is emitted when RetryFetchSpecies intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.speciesViewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        repository.specieResponseType = ResponseType.ERROR

        viewModel.processIntent(
            flowOf(CharacterDetailViewIntent.RetryFetchSpecie(DummyData.characterModel.url))
        )

        assertThat(stateRecorder.takeAll())
            .containsElements(
                SpecieDetailViewState.Loading,
                SpecieDetailViewState.Error(DummyData.exception.errorMessage)
            )
    }
}
