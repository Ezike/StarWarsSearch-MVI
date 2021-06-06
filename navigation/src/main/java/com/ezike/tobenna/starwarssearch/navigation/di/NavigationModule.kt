package com.ezike.tobenna.starwarssearch.navigation.di

import com.ezike.tobenna.starwarssearch.character_search.navigation.Navigator
import com.ezike.tobenna.starwarssearch.navigation.SearchScreenNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
interface NavigationModule {

    @get:Binds
    val SearchScreenNavigator.searchScreenNavigator: Navigator
}
