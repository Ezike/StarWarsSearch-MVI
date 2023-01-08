package com.ezike.tobenna.starwarssearch.charactersearch.presentation

import com.ezike.tobenna.starwarssearch.charactersearch.presentation.viewstate.SearchScreenState
import com.ezike.tobenna.starwarssearch.presentation.base.IntentProcessor
import com.ezike.tobenna.starwarssearch.presentation.base.StateReducer
import com.ezike.tobenna.starwarssearch.presentation.stateMachine.StateMachine
import com.ezike.tobenna.starwarssearch.presentation_android.ComponentManager

internal typealias SearchIntentProcessor =
    @JvmSuppressWildcards IntentProcessor<SearchScreenResult>

internal typealias SearchStateReducer =
    @JvmSuppressWildcards StateReducer<SearchScreenState, SearchScreenResult>

internal typealias SearchStateMachine =
    @JvmSuppressWildcards StateMachine<SearchScreenState, SearchScreenResult>

internal typealias SearchComponentManager =
    @JvmSuppressWildcards ComponentManager<SearchScreenState, SearchScreenResult>
