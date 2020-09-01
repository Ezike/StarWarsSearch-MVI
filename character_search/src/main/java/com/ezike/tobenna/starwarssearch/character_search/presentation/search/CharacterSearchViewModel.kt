package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.search.LoadSearchHistory
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIPresenter
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn

class CharacterSearchViewModel @ViewModelInject constructor(
    private val searchStateMachine: SearchStateMachine
) : ViewModel(), MVIPresenter<SearchViewState, ViewIntent> {

    override val viewState: StateFlow<SearchViewState>
        get() = searchStateMachine.viewState

    init {
        processIntent(LoadSearchHistory)
        searchStateMachine.processor.launchIn(viewModelScope)
    }

    override fun processIntent(intent: ViewIntent) {
        searchStateMachine
            .processIntents(flowOf(intent))
            .launchIn(viewModelScope)
    }
}
