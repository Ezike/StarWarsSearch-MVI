package com.ezike.tobenna.starwarssearch.cache.entity

import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity

internal object DummyData {
    val entity = CharacterEntity(
        "Harry potter",
        "34.BBY",
        "111",
        "http://swapi.dev/api/people/12/"
    )

    val characterModel = CharacterCacheModel(
        71,
        "Harry Granger",
        "34.BBY",
        "111",
        "http://swapi.dev/api/people/2/"
    )
}
