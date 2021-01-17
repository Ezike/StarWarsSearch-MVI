package com.ezike.tobenna.starwarssearch.character_search.di

import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateReducer
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchViewStateReducer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface SearchCharacterModule {

    @get:Binds
    val SearchViewIntentProcessor.intentProcessor: SearchIntentProcessor

    @get:Binds
    val SearchViewStateReducer.reducer: SearchStateReducer

    @get:Binds
    val SearchViewStateMachine.stateMachine: SearchStateMachine
}
