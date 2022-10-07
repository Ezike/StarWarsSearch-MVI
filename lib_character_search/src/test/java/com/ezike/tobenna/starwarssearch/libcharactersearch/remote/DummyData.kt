package com.ezike.tobenna.starwarssearch.libcharactersearch.remote

import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.CharacterRemoteModel

internal object DummyData {

    val characterRemoteModel = CharacterRemoteModel(
        "Luke",
        "32.BBY",
        "100",
        listOf("https:swapi.dev/film/1"),
        "https:swapi.dev/planet/1",
        listOf("https:swapi.dev/film/1"),
        "https:swapi.dev/people/1"
    )
}
