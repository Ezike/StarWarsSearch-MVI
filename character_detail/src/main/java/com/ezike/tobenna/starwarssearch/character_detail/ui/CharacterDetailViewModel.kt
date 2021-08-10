package com.ezike.tobenna.starwarssearch.character_detail.ui

import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.character_detail.presentation.CharacterDetailViewStateMachine
import com.ezike.tobenna.starwarssearch.character_detail.presentation.DetailComponentManager
import com.ezike.tobenna.starwarssearch.presentation_android.AssistedCreator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CharacterDetailViewModel @AssistedInject constructor(
    stateMachine: CharacterDetailViewStateMachine.Factory,
    @Assisted character: CharacterDetailModel
) : DetailComponentManager(stateMachine.create(character)) {

    @AssistedFactory
    interface Creator : AssistedCreator<CharacterDetailModel, CharacterDetailViewModel>
}
