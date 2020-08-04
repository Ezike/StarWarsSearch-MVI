package com.ezike.tobenna.starwarssearch.character_search.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import javax.inject.Inject

class NavigationDispatcherImpl @Inject constructor(
    private val navController: NavController
) : NavigationDispatcher {

    override fun openCharacterDetail(model: CharacterModel) {
        navController.navigate(
            R.id.characterDetailFragment,
            bundleOf(CHARACTER_ARG to model)
        )
    }

    override fun goBack() {
        navController.navigateUp()
    }

    companion object {
        const val CHARACTER_ARG: String = "character"
    }
}
