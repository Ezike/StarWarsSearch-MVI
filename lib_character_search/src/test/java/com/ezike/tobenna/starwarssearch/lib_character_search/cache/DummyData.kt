package com.ezike.tobenna.starwarssearch.lib_character_search.cache

import com.ezike.tobenna.starwarssearch.cache.model.CharacterCacheModel
import com.ezike.tobenna.starwarssearch.cache.model.CharacterDetailCacheModel
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity

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
