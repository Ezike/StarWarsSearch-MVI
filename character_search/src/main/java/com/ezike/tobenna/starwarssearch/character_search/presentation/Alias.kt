package com.ezike.tobenna.starwarssearch.character_search.presentation

import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.CharacterDetailViewResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchScreenResult
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchScreenState
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.IntentProcessor
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewStateReducer
import com.ezike.tobenna.starwarssearch.presentation.mvi.stateMachine.StateMachine
import com.ezike.tobenna.starwarssearch.presentation_android.ComponentManager

typealias SearchIntentProcessor =
    @JvmSuppressWildcards IntentProcessor<SearchScreenResult>

typealias SearchStateReducer =
    @JvmSuppressWildcards ViewStateReducer<SearchScreenState, SearchScreenResult>

typealias SearchStateMachine =
    @JvmSuppressWildcards StateMachine<SearchScreenState, SearchScreenResult>

typealias SearchComponentManager =
    @JvmSuppressWildcards ComponentManager<SearchScreenState, SearchScreenResult>

typealias CharacterDetailIntentProcessor =
    @JvmSuppressWildcards IntentProcessor<CharacterDetailViewResult>

typealias CharacterDetailStateReducer =
    @JvmSuppressWildcards ViewStateReducer<CharacterDetailViewState, CharacterDetailViewResult>

typealias CharacterDetailStateMachine =
    @JvmSuppressWildcards StateMachine<CharacterDetailViewState, CharacterDetailViewResult>

typealias DetailComponentManager =
    @JvmSuppressWildcards ComponentManager<CharacterDetailViewState, CharacterDetailViewResult>
