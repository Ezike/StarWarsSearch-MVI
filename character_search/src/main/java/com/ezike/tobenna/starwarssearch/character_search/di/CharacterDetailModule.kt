package com.ezike.tobenna.starwarssearch.character_search.di

import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateReducer
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.CharacterDetailViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.CharacterDetailViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.CharacterDetailViewStateReducer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
interface CharacterDetailModule {

    @get:Binds
    val CharacterDetailViewIntentProcessor.intentProcessor: CharacterDetailIntentProcessor

    @get:Binds
    val CharacterDetailViewStateReducer.reducer: CharacterDetailStateReducer

    @get:Binds
    val CharacterDetailViewStateMachine.stateMachine: CharacterDetailStateMachine
}
