package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.FilmModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.PlanetModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.SpecieModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewStateReducer
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.LoadCharacterDetailIntent
import com.ezike.tobenna.starwarssearch.character_search.views.detail.RetryFetchCharacterDetailsIntent
import com.ezike.tobenna.starwarssearch.character_search.views.detail.RetryFetchFilmIntent
import com.ezike.tobenna.starwarssearch.character_search.views.detail.RetryFetchPlanetIntent
import com.ezike.tobenna.starwarssearch.character_search.views.detail.RetryFetchSpecieIntent
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

        val initialState = CharacterDetailViewState()
        val character: CharacterModel = DummyData.characterModel

        viewModel.processIntent(LoadCharacterDetailIntent(character))

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    initialState,
                    initialState.copy(character),
                    initialState.copy(character).filmState { success(listOf(DummyData.filmModel)) },
                    initialState.copy(character).filmState { success(listOf(DummyData.filmModel)) }
                        .planetState { success(DummyData.planetModel) },
                    initialState.copy(character).filmState { success(listOf(DummyData.filmModel)) }
                        .planetState { success(DummyData.planetModel) }
                        .specieState { success(listOf(DummyData.specieModel)) }
                )
            )
    }

    @Test
    fun `check that Retrying viewState is emitted when RetryFetchCharacterDetails intent is called`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val initialState = CharacterDetailViewState()
        val character: CharacterModel = DummyData.characterModel

        repository.characterResponseType = ResponseType.ERROR
        viewModel.processIntent(LoadCharacterDetailIntent(character))
        repository.characterResponseType = ResponseType.DATA
        viewModel.processIntent(RetryFetchCharacterDetailsIntent(character))

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    initialState,
                    initialState.copy(character),
                    initialState.copy(character)
                        .errorState(character.name, DummyData.exception.errorMessage),
                    initialState.copy(character)
                        .errorState(character.name, DummyData.exception.errorMessage).retry,
                    initialState.copy(character)
                        .errorState(
                            character.name,
                            DummyData.exception.errorMessage
                        ).retry.filmState { success(listOf(DummyData.filmModel)) },
                    initialState.copy(character)
                        .errorState(
                            character.name,
                            DummyData.exception.errorMessage
                        ).retry.filmState { success(listOf(DummyData.filmModel)) }
                        .planetState { success(DummyData.planetModel) },
                    initialState.copy(character).errorState(
                        character.name,
                        DummyData.exception.errorMessage
                    ).retry.filmState { success(listOf(DummyData.filmModel)) }
                        .planetState { success(DummyData.planetModel) }
                        .specieState { success(listOf(DummyData.specieModel)) }
                )
            )
    }

    @Test
    fun `check that FetchCharacterDetailError viewState is emitted when LoadCharacterDetail intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        repository.characterResponseType = ResponseType.ERROR

        val initialState = CharacterDetailViewState()
        val character: CharacterModel = DummyData.characterModel

        viewModel.processIntent(LoadCharacterDetailIntent(character))

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    initialState,
                    initialState.copy(character),
                    initialState.copy(character)
                        .errorState(character.name, DummyData.exception.errorMessage)
                )
            )
    }

    @Test
    fun `check that FilmDetailViewState Error viewState is emitted when RetryFetchFilms intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val initialState = CharacterDetailViewState()
        repository.filmResponseType = ResponseType.ERROR

        viewModel.processIntent(RetryFetchFilmIntent(DummyData.characterModel.url))

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    initialState,
                    initialState.filmState { error(DummyData.exception.errorMessage) }
                )
            )
    }

    @Test
    fun `check that PlanetDetailViewState error viewState is emitted when RetryFetchPlanet intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val initialState = CharacterDetailViewState()
        repository.planetResponseType = ResponseType.ERROR

        viewModel.processIntent(RetryFetchPlanetIntent(DummyData.characterModel.url))

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    initialState,
                    initialState.planetState { error(DummyData.exception.errorMessage) }
                )
            )
    }

    @Test
    fun `check that SpecieDetailViewState Error viewState is emitted when RetryFetchSpecies intent fails`() {
        mainCoroutineRule.pauseDispatcher()
        viewModel.viewState.recordWith(stateRecorder)
        mainCoroutineRule.resumeDispatcher()

        val initialState = CharacterDetailViewState()
        repository.specieResponseType = ResponseType.ERROR

        viewModel.processIntent(RetryFetchSpecieIntent(DummyData.characterModel.url))

        assertThat(stateRecorder.takeAll())
            .containsExactlyElementsIn(
                arrayOf(
                    initialState,
                    initialState.specieState { error(DummyData.exception.errorMessage) }
                )
            )
    }
}
