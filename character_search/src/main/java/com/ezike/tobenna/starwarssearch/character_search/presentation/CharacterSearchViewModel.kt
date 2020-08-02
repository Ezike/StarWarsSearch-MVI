package com.ezike.tobenna.starwarssearch.character_search.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezike.tobenna.starwarssearch.character_search.di.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn

class CharacterSearchViewModel @ViewModelInject constructor(
    private val searchStateMachine: SearchStateMachine
) : ViewModel() {

    val viewState: StateFlow<SearchViewState>
        get() = searchStateMachine.viewState

    init {
        searchStateMachine.processor.launchIn(viewModelScope)
    }

    fun processIntent(intents: Flow<SearchViewIntent>) {
        searchStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }
}
