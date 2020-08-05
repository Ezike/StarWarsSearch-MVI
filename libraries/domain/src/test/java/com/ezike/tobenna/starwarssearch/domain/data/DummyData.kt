package com.ezike.tobenna.starwarssearch.domain.data

import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.domain.model.Specie

internal object DummyData {
    const val name = "Anakin"
    val character = Character(
        "Luke Skywalker",
        "201BBY",
        "11",
        "https://swapi.dev/12/"
    )

    val characterDetail = CharacterDetail(
        listOf("www.url.com"),
        "http://swapi.dev/planet",
        listOf("https://swapi.dev.people"),
        "https://swapi.dev/people/12/"
    )

    val film = Film(
        "Some title",
        "An opening crawl"
    )

    val planet = Planet(
        "tatooine",
        "1000000"
    )

    val specie = Specie(
        "Iroko",
        "Yoruba",
        "Enugu"
    )
}
