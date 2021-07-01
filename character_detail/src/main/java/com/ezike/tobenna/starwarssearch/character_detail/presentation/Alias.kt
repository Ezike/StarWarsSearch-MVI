package com.ezike.tobenna.starwarssearch.character_detail.presentation

import com.ezike.tobenna.starwarssearch.character_detail.presentation.viewstate.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.IntentProcessor
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewStateReducer
import com.ezike.tobenna.starwarssearch.presentation.mvi.stateMachine.StateMachine
import com.ezike.tobenna.starwarssearch.presentation_android.ComponentManager

typealias CharacterDetailIntentProcessor =
    @JvmSuppressWildcards IntentProcessor<CharacterDetailViewResult>

typealias CharacterDetailStateReducer =
    @JvmSuppressWildcards ViewStateReducer<CharacterDetailViewState, CharacterDetailViewResult>

typealias CharacterDetailStateMachine =
    @JvmSuppressWildcards StateMachine<CharacterDetailViewState, CharacterDetailViewResult>

typealias DetailComponentManager =
    @JvmSuppressWildcards ComponentManager<CharacterDetailViewState, CharacterDetailViewResult>
