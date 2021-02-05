package com.ezike.tobenna.starwarssearch.character_search.navigation

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel

// TODO refactor navigation entirely
// TODO refactor modularisation entirely

interface NavigationDispatcher {
    fun openCharacterDetail(model: CharacterModel)
    fun goBack()
}
