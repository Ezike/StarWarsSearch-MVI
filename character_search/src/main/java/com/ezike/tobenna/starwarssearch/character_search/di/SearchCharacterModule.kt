package com.ezike.tobenna.starwarssearch.character_search.di

import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewStateReducer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
interface SearchCharacterModule {

    @get:Binds
    val SearchViewIntentProcessor.intentProcessor: SearchIntentProcessor

    @get:Binds
    val SearchViewStateReducer.reducer: SearchStateReducer

    @get:Binds
    val SearchViewStateMachine.stateMachine: SearchStateMachine
}
