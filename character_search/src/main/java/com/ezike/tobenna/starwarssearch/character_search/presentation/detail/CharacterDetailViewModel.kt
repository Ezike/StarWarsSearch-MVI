package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn

class CharacterDetailViewModel @ViewModelInject constructor(
    private val characterDetailStateMachine: CharacterDetailStateMachine
) : ViewModel() {

    val viewState: StateFlow<CharacterDetailViewState>
        get() = characterDetailStateMachine.viewState

    init {
        characterDetailStateMachine.processor.launchIn(viewModelScope)
    }

    fun processIntent(intents: Flow<CharacterDetailViewIntent>) {
        characterDetailStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }
}
