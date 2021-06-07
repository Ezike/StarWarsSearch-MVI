package com.ezike.tobenna.starwarssearch.navigation.di

import androidx.navigation.NavController
import com.ezike.tobenna.starwarssearch.character_search.navigation.Navigator
import com.ezike.tobenna.starwarssearch.core.ext.NavigateBack
import com.ezike.tobenna.starwarssearch.navigation.SearchScreenNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
internal interface NavigationModule {

    @get:Binds
    val SearchScreenNavigator.searchScreenNavigator: Navigator

    companion object {
        @Provides
        fun provideBackNav(
            navController: NavController
        ): NavigateBack = {
            navController.navigateUp()
        }
    }
}
