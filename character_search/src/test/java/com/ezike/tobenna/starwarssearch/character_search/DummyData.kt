package com.ezike.tobenna.starwarssearch.character_search

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.domain.model.Character

internal object DummyData {
    val characterModel = CharacterModel(
        "Many men",
        "34.BBY",
        "143",
        "https://swapi.dev/people/21"
    )

    val character = Character(
        "Many men",
        "34.BBY",
        "143",
        "https://swapi.dev/people/21"
    )
}
