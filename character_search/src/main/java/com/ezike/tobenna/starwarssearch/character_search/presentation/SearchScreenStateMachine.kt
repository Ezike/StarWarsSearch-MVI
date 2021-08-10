package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.character_search.presentation.viewstate.SearchScreenStateFactory
import com.ezike.tobenna.starwarssearch.presentation.stateMachine.RenderStrategy
import javax.inject.Inject

class SearchScreenStateMachine @Inject constructor(
    intentProcessor: SearchIntentProcessor,
    reducer: SearchStateReducer
) : SearchStateMachine(
    intentProcessor,
    reducer,
    SearchScreenStateFactory.initialState,
    LoadSearchHistory,
    RenderStrategy.Latest
)
