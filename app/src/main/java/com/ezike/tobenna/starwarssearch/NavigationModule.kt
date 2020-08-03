package com.ezike.tobenna.starwarssearch

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ezike.tobenna.starwarssearch.character_search.navigation.NavigationDispatcher
import com.ezike.tobenna.starwarssearch.character_search.navigation.NavigationDispatcherImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
interface NavigationModule {

    @get:Binds
    val NavigationDispatcherImpl.navigationDispatcher: NavigationDispatcher

    companion object {
        @Provides
        fun provideNavController(activity: Activity): NavController =
            activity.findNavController(R.id.authHostFragment)
    }
}
