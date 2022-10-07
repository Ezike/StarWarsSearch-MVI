package com.ezike.tobenna.starwarssearch.navigation

import androidx.navigation.NavController
import com.ezike.tobenna.starwarssearch.characterdetail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel
import com.ezike.tobenna.starwarssearch.charactersearch.navigation.Navigator
import javax.inject.Inject

internal class SearchScreenNavigator @Inject constructor(
    private val navController: NavController
) : Navigator {

    override fun openCharacterDetail(model: CharacterModel) {
        navController.navigate(
            NavigationRootDirections.openDetail(
                character = model.toDetail()
            )
        )
    }
}

internal fun CharacterModel.toDetail() =
    CharacterDetailModel(
        name = name,
        birthYear = birthYear,
        heightCm = heightCm,
        url = url
    )
