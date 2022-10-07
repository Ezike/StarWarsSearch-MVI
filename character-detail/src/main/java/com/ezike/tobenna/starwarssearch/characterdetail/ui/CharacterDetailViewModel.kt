package com.ezike.tobenna.starwarssearch.characterdetail.ui

import com.ezike.tobenna.starwarssearch.characterdetail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.characterdetail.presentation.CharacterDetailViewStateMachine
import com.ezike.tobenna.starwarssearch.characterdetail.presentation.DetailComponentManager
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
