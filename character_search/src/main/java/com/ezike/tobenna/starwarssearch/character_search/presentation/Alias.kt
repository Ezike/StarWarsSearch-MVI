package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.IntentProcessor
import com.ezike.tobenna.starwarssearch.presentation.mvi.StateMachine
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewStateReducer

typealias SearchIntentProcessor =
    @JvmSuppressWildcards IntentProcessor<ViewIntent, SearchViewResult>

typealias SearchStateReducer =
    @JvmSuppressWildcards ViewStateReducer<SearchViewState, SearchViewResult>

typealias SearchStateMachine =
    @JvmSuppressWildcards StateMachine<ViewIntent, SearchViewState, SearchViewResult>

typealias CharacterDetailIntentProcessor =
    @JvmSuppressWildcards IntentProcessor<ViewIntent, CharacterDetailViewResult>

typealias CharacterDetailStateReducer =
    @JvmSuppressWildcards ViewStateReducer<CharacterDetailViewState, CharacterDetailViewResult>

typealias CharacterDetailStateMachine =
    @JvmSuppressWildcards StateMachine<ViewIntent, CharacterDetailViewState, CharacterDetailViewResult>
