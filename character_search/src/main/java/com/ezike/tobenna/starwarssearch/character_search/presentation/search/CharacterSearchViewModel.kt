package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIPresenter
import com.ezike.tobenna.starwarssearch.presentation.mvi.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.mvi.UIComponent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn

class CharacterSearchViewModel @ViewModelInject constructor(
    private val searchStateMachine: SearchStateMachine
) : ViewModel(), MVIPresenter<SearchViewState> {

    init {
        searchStateMachine.processor.launchIn(viewModelScope)
    }

    override fun processIntent(intent: ViewIntent) {
        searchStateMachine
            .processIntents(flowOf(intent))
            .launchIn(viewModelScope)
    }

    override fun <V : ViewState> subscribe(
        component: UIComponent<V>,
        transform: StateTransform<SearchViewState, V>
    ) {
        searchStateMachine.subscribe(component, transform)
    }

    override fun onCleared() {
        searchStateMachine.unSubscribe()
    }
}
