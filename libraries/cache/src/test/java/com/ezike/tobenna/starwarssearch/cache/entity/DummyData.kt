package com.ezike.tobenna.starwarssearch.cache.entity

import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity

internal object DummyData {
    val entity = CharacterEntity(
        "Harry potter",
        "34.BBY",
        "111",
        "http://swapi.dev/api/people/12/"
    )

    val characterModel = CharacterCacheModel(
        "Harry Granger",
        "34.BBY",
        "111",
        "http://swapi.dev/api/people/2/"
    )

    val characterDetailEntity = CharacterDetailEntity(
        listOf("www.url.com"),
        "http://swapi.dev/planet",
        listOf("https://swapi.dev.people"),
        "https://swapi.dev/people/12/"
    )

    val characterDetailModel = CharacterDetailCacheModel(
        listOf("www.ul.com"),
        "http://swapi.dv/planet",
        listOf("https://swapi.dev.people"),
        "https://swapi.dev/people/12/"
    )
}
