package com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi

import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateReducer
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
class CharacterDetailViewStateMachine @Inject constructor(
    intentProcessor: CharacterDetailIntentProcessor,
    reducer: CharacterDetailStateReducer
) : CharacterDetailStateMachine(
    intentProcessor,
    reducer,
    CharacterDetailViewIntent.Idle,
    CharacterDetailViewState.Idle
)
