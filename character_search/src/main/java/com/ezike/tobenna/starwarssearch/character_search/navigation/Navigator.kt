package com.ezike.tobenna.starwarssearch.character_search.navigation

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel

interface Navigator {
    fun openCharacterDetail(model: CharacterModel)
}
