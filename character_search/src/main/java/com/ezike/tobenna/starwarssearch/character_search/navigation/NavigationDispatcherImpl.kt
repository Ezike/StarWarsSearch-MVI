package com.ezike.tobenna.starwarssearch.character_search.navigation

import androidx.navigation.NavController
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import javax.inject.Inject

class NavigationDispatcherImpl @Inject constructor(
    private val navController: NavController
) : NavigationDispatcher {

    override fun openCharacterDetail(model: CharacterModel) {
    }

    override fun goBack() {
        navController.navigateUp()
    }

    companion object {
        const val CHARACTER_ARG: String = "character"
    }
}
