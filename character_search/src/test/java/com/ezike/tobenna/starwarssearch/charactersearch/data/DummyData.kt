package com.ezike.tobenna.starwarssearch.charactersearch.data

import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.testutils.ERROR_MSG
import java.net.SocketTimeoutException

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

    val characterList: List<Character> = listOf(character)

    const val query = "Luke"

    val characterDetail = CharacterDetail(
        listOf("www.url.com"),
        "http://swapi.dev/planet",
        listOf("https://swapi.dev.people"),
        "https://swapi.dev/people/12/"
    )

    val exception: SocketTimeoutException
        get() = SocketTimeoutException(ERROR_MSG)
}
