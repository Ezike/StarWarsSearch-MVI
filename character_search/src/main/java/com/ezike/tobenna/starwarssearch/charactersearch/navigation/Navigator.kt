package com.ezike.tobenna.starwarssearch.charactersearch.navigation

import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel

interface Navigator {
    fun openCharacterDetail(model: CharacterModel)
}
