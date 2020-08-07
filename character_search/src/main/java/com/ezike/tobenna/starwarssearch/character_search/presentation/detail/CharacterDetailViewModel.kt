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
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

class CharacterDetailViewModel @ViewModelInject constructor(
    private val characterDetailStateMachine: CharacterDetailStateMachine
) : ViewModel(), MVIPresenter<CharacterDetailViewState, CharacterDetailViewIntent> {

    /**
     * Separating the viewStates into different flows so that the last state for each view
     * component - [PlanetView], [SpecieView], [FilmsView], [ProfileView] is always cached
     * in its corresponding flow.
     *
     * Comes in handy during config changes
     */
    private val detailViewState: MutableStateFlow<CharacterDetailViewState> =
        MutableStateFlow(CharacterDetailViewState.Idle)

    private val filmViewState: MutableStateFlow<FilmDetailViewState> =
        MutableStateFlow(FilmDetailViewState.Loading)

    private val speciesViewState: MutableStateFlow<SpecieDetailViewState> =
        MutableStateFlow(SpecieDetailViewState.Loading)

    private val planetViewState: MutableStateFlow<PlanetDetailViewState> =
        MutableStateFlow(PlanetDetailViewState.Loading)

    override val viewState: Flow<CharacterDetailViewState>
        get() = merge(
            detailViewState,
            planetViewState,
            speciesViewState,
            filmViewState
        )

    init {
        characterDetailStateMachine.processor.launchIn(viewModelScope)
        makeViewState()
    }

    private fun makeViewState() {
        characterDetailStateMachine.viewState.onEach { state ->
            when (state) {
                is PlanetDetailViewState -> planetViewState.value = state
                is SpecieDetailViewState -> speciesViewState.value = state
                is FilmDetailViewState -> filmViewState.value = state
                else -> detailViewState.value = state
            }
        }.launchIn(viewModelScope)
    }

    override fun processIntent(intents: Flow<CharacterDetailViewIntent>) {
        characterDetailStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }
}
