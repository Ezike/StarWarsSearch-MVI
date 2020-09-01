package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIPresenter
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn

class CharacterDetailViewModel @ViewModelInject constructor(
    private val characterDetailStateMachine: CharacterDetailStateMachine
) : ViewModel(), MVIPresenter<CharacterDetailViewState, ViewIntent> {

    override val viewState: Flow<CharacterDetailViewState>
        get() = characterDetailStateMachine.viewState

    init {
        characterDetailStateMachine.processor.launchIn(viewModelScope)
    }

    override fun processIntent(intent: ViewIntent) {
        characterDetailStateMachine
            .processIntents(flowOf(intent))
            .launchIn(viewModelScope)
    }
}
