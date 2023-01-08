package com.ezike.tobenna.starwarssearch.characterdetail.presentation

import com.ezike.tobenna.starwarssearch.characterdetail.presentation.viewstate.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.presentation.base.IntentProcessor
import com.ezike.tobenna.starwarssearch.presentation.base.StateReducer
import com.ezike.tobenna.starwarssearch.presentation.stateMachine.StateMachine
import com.ezike.tobenna.starwarssearch.presentation_android.ComponentManager

internal typealias CharacterDetailIntentProcessor =
        @JvmSuppressWildcards IntentProcessor<CharacterDetailViewResult>

internal typealias CharacterDetailStateReducer =
        @JvmSuppressWildcards StateReducer<CharacterDetailViewState, CharacterDetailViewResult>

internal typealias CharacterDetailStateMachine =
        @JvmSuppressWildcards StateMachine<CharacterDetailViewState, CharacterDetailViewResult>

internal typealias DetailComponentManager =
        @JvmSuppressWildcards ComponentManager<CharacterDetailViewState, CharacterDetailViewResult>
