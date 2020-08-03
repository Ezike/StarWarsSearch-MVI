package com.ezike.tobenna.starwarssearch.character_search.presentation.mvi

import com.ezike.tobenna.starwarssearch.character_search.di.SearchIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.di.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.di.SearchStateReducer
import javax.inject.Inject

class SearchViewStateMachine @Inject constructor(
    intentProcessor: SearchIntentProcessor,
    reducer: SearchStateReducer
) : SearchStateMachine(
    intentProcessor,
    reducer,
    SearchHistoryViewIntent.LoadSearchHistory,
    SearchViewState.Idle
)
