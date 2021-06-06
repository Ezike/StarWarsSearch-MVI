package com.ezike.tobenna.starwarssearch.character_detail.di

import com.ezike.tobenna.starwarssearch.character_detail.presentation.CharacterDetailIntentProcessor
import com.ezike.tobenna.starwarssearch.character_detail.presentation.CharacterDetailStateReducer
import com.ezike.tobenna.starwarssearch.character_detail.presentation.CharacterDetailViewIntentProcessor
import com.ezike.tobenna.starwarssearch.character_detail.presentation.CharacterDetailViewStateReducer
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
}
