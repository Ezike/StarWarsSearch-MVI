package com.ezike.tobenna.starwarssearch.character_detail.presentation

import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.character_detail.ui.LoadCharacterDetailIntent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CharacterDetailViewStateMachine @AssistedInject constructor(
    intentProcessor: CharacterDetailIntentProcessor,
    reducer: CharacterDetailStateReducer,
    @Assisted character: CharacterDetailModel
) : CharacterDetailStateMachine(
    intentProcessor,
    reducer,
    CharacterDetailViewState.init,
    LoadCharacterDetailIntent(character)
) {

    @AssistedFactory
    interface Factory {
        fun create(character: CharacterDetailModel): CharacterDetailViewStateMachine
    }
}
