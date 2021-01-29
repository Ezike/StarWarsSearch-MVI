package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateReducer
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.LoadCharacterDetailIntent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class CharacterDetailViewStateMachine @AssistedInject constructor(
    intentProcessor: CharacterDetailIntentProcessor,
    reducer: CharacterDetailStateReducer,
    @Assisted character: CharacterModel
) : CharacterDetailStateMachine(
    intentProcessor,
    reducer,
    CharacterDetailViewState.init,
    LoadCharacterDetailIntent(character)
) {

    @AssistedFactory
    interface Factory {
        fun create(character: CharacterModel): CharacterDetailViewStateMachine
    }
}
