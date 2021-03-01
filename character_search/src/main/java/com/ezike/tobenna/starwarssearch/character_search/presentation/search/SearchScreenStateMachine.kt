package com.ezike.tobenna.starwarssearch.character_search.presentation.search

import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateReducer
import com.ezike.tobenna.starwarssearch.presentation.mvi.stateMachine.Latest
import javax.inject.Inject

class SearchScreenStateMachine @Inject constructor(
    intentProcessor: SearchIntentProcessor,
    reducer: SearchStateReducer
) : SearchStateMachine(
    intentProcessor,
    reducer,
    SearchScreenState.Initial,
    LoadSearchHistory,
    Latest
)
