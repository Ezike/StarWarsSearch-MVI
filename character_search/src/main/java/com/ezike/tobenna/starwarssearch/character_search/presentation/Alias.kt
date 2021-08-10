package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.character_search.presentation.viewstate.SearchScreenState
import com.ezike.tobenna.starwarssearch.presentation.base.IntentProcessor
import com.ezike.tobenna.starwarssearch.presentation.base.StateReducer
import com.ezike.tobenna.starwarssearch.presentation.stateMachine.StateMachine
import com.ezike.tobenna.starwarssearch.presentation_android.ComponentManager

typealias SearchIntentProcessor =
    @JvmSuppressWildcards IntentProcessor<SearchScreenResult>

typealias SearchStateReducer =
    @JvmSuppressWildcards StateReducer<SearchScreenState, SearchScreenResult>

typealias SearchStateMachine =
    @JvmSuppressWildcards StateMachine<SearchScreenState, SearchScreenResult>

typealias SearchComponentManager =
    @JvmSuppressWildcards ComponentManager<SearchScreenState, SearchScreenResult>
