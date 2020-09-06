package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import com.ezike.tobenna.starwarssearch.character_search.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.FilmModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.PlanetModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.SpecieModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewStateReducer
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchFilms
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchPlanet
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchSpecies
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.GetCharacterDetail
import com.ezike.tobenna.starwarssearch.testutils.FlowRecorder
import com.ezike.tobenna.starwarssearch.testutils.MainCoroutineRule
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule

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
}
