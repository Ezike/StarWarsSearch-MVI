package com.ezike.tobenna.starwarssearch.data

import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.domain.model.Specie

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

    val characterDetailEntity = CharacterDetailEntity(
        listOf("www.url.com"),
        "http://swapi.dev/planet",
        listOf("https://swapi.dev.people"),
        "https://swapi.dev/people/12/"
    )

    val filmEntity = FilmEntity(
        "Some title",
        "An opening crawl"
    )

    val planetEntity = PlanetEntity(
        "tatooine",
        "1000000"
    )

    val specieEntity = SpecieEntity(
        "Iroko",
        "Yoruba",
        "Enugu"
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
