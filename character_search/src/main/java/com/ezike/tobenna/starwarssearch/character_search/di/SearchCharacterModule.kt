package com.ezike.tobenna.starwarssearch.character_search.di

import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchScreenIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchScreenStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchScreenStateReducer
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateReducer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface SearchCharacterModule {

    @get:Binds
    val SearchScreenIntentProcessor.intentProcessor: SearchIntentProcessor

    @get:Binds
    val SearchScreenStateReducer.reducer: SearchStateReducer

    @get:Binds
    val SearchScreenStateMachine.stateMachine: SearchStateMachine
}
