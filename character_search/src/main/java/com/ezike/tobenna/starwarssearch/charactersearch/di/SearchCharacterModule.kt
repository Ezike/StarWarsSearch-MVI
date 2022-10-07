package com.ezike.tobenna.starwarssearch.charactersearch.di

import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchIntentProcessor
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchScreenIntentProcessor
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchScreenStateMachine
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchScreenStateReducer
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchStateMachine
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchStateReducer
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
