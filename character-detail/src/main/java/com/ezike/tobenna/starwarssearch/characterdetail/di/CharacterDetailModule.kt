package com.ezike.tobenna.starwarssearch.characterdetail.di

import com.ezike.tobenna.starwarssearch.characterdetail.presentation.CharacterDetailIntentProcessor
import com.ezike.tobenna.starwarssearch.characterdetail.presentation.CharacterDetailStateReducer
import com.ezike.tobenna.starwarssearch.characterdetail.presentation.CharacterDetailViewIntentProcessor
import com.ezike.tobenna.starwarssearch.characterdetail.presentation.CharacterDetailViewStateReducer
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
