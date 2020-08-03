package com.ezike.tobenna.starwarssearch.character_search.navigation

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel

interface NavigationDispatcher {
    fun openCharacterDetail(model: CharacterModel)
    fun goBack()
}
