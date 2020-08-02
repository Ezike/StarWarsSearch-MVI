package com.ezike.tobenna.starwarssearch.data

import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.domain.model.Character

internal object DummyData {

    const val name: String = "leia"
    val characterEntity = CharacterEntity(
        "Leia organa",
        "21BBA",
        "5'5",
        "https://swapi.dev/people/11/"
    )

    val character = Character(
        "Leia organa",
        "21BBA",
        "5'5",
        "https://swapi.dev/people/11/"
    )
}
