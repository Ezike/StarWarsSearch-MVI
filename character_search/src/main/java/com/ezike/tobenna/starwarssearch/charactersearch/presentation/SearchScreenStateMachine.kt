package com.ezike.tobenna.starwarssearch.charactersearch.presentation

import com.ezike.tobenna.starwarssearch.charactersearch.presentation.viewstate.SearchScreenState
import com.ezike.tobenna.starwarssearch.presentation.stateMachine.RenderStrategy
import javax.inject.Inject

internal class SearchScreenStateMachine @Inject constructor(
    intentProcessor: SearchIntentProcessor,
    reducer: SearchStateReducer
) : SearchStateMachine(
    intentProcessor = intentProcessor,
    reducer = reducer,
    initialState = SearchScreenState.Initial,
    initialIntent = LoadSearchHistory,
    renderStrategy = RenderStrategy.Latest
)
