package com.ezike.tobenna.starwarssearch.character_search.di

import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.IntentProcessor
import com.ezike.tobenna.starwarssearch.presentation.mvi.StateMachine
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewStateReducer

typealias SearchIntentProcessor =
    @JvmSuppressWildcards IntentProcessor<SearchViewIntent, SearchViewResult>

typealias SearchStateReducer =
    @JvmSuppressWildcards ViewStateReducer<SearchViewState, SearchViewResult>

typealias SearchStateMachine =
    @JvmSuppressWildcards StateMachine<SearchViewIntent, SearchViewState, SearchViewResult>
