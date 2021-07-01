package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.character_search.presentation.viewstate.SearchScreenStateFactory
import com.ezike.tobenna.starwarssearch.presentation.mvi.stateMachine.Latest
import javax.inject.Inject

class SearchScreenStateMachine @Inject constructor(
    intentProcessor: SearchIntentProcessor,
    reducer: SearchStateReducer
) : SearchStateMachine(
    intentProcessor,
    reducer,
    SearchScreenStateFactory.initialState,
    LoadSearchHistory,
    Latest
)
