package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.FilmDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.PlanetDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.SpecieDetailViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharacterDetailViewModel @ViewModelInject constructor(
    private val characterDetailStateMachine: CharacterDetailStateMachine
) : ViewModel() {

    /**
     * Separating the viewStates into different flows so that the last state for each view
     * component [PlanetView], [SpecieView], [FilmsView], [ProfileView] is always cached in its
     * corresponding flow.
     *
     * Comes in handy during config changes
     */
    private val mutableDetailViewState: MutableStateFlow<CharacterDetailViewState> =
        MutableStateFlow(CharacterDetailViewState.Idle)
    val detailViewState: StateFlow<CharacterDetailViewState> get() = mutableDetailViewState

    private val mutableFilmViewState: MutableStateFlow<FilmDetailViewState> =
        MutableStateFlow(FilmDetailViewState.Loading)
    val filmViewState: StateFlow<FilmDetailViewState> get() = mutableFilmViewState

    private val mutableSpeciesViewState: MutableStateFlow<SpecieDetailViewState> =
        MutableStateFlow(SpecieDetailViewState.Loading)
    val speciesViewState: StateFlow<SpecieDetailViewState> get() = mutableSpeciesViewState

    private val mutablePlanetViewState: MutableStateFlow<PlanetDetailViewState> =
        MutableStateFlow(PlanetDetailViewState.Loading)
    val planetViewState: StateFlow<PlanetDetailViewState> get() = mutablePlanetViewState

    init {
        characterDetailStateMachine.processor.launchIn(viewModelScope)
        makeViewState()
    }

    private fun makeViewState() {
        characterDetailStateMachine.viewState.onEach { state ->
            when (state) {
                is PlanetDetailViewState -> mutablePlanetViewState.value = state
                is SpecieDetailViewState -> mutableSpeciesViewState.value = state
                is FilmDetailViewState -> mutableFilmViewState.value = state
                CharacterDetailViewState.Idle -> mutableDetailViewState.value = state
                is CharacterDetailViewState.ProfileLoaded -> mutableDetailViewState.value = state
                is CharacterDetailViewState.FetchDetailError -> mutableDetailViewState.value = state
            }
        }.launchIn(viewModelScope)
    }

    fun processIntent(intents: Flow<CharacterDetailViewIntent>) {
        characterDetailStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }
}
