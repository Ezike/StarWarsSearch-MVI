package com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi

import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateReducer
import com.ezike.tobenna.starwarssearch.character_search.ui.search.LoadSearchHistory
import javax.inject.Inject

class SearchViewStateMachine @Inject constructor(
    intentProcessor: SearchIntentProcessor,
    reducer: SearchStateReducer
) : SearchStateMachine(
    intentProcessor,
    reducer,
    SearchViewState.init,
    LoadSearchHistory
)
