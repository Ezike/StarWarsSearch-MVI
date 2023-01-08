package com.ezike.tobenna.starwarssearch.characterdetail.data

import retrofit2.http.GET
import retrofit2.http.Url

internal interface ApiService {

    @GET
    suspend fun fetchCharacterDetail(@Url url: String): CharacterRemoteModel

    @GET
    suspend fun fetchSpecieDetails(@Url speciesUrl: String): SpecieEntity

    @GET
    suspend fun fetchFilmDetails(@Url filmsUrl: String): FilmEntity

    @GET
    suspend fun fetchPlanet(@Url characterUrl: String): PlanetEntity
}

internal data class FilmEntity(
    val title: String,
    val opening_crawl: String
)

internal data class PlanetEntity(
    val name: String,
    val population: String,
)

internal data class SpecieEntity(
    val name: String,
    val language: String,
    val homeworld: String?
)

internal data class CharacterRemoteModel(
    val name: String,
    val birth_year: String,
    val height: String,
    val films: List<String>,
    val homeworld: String,
    val species: List<String>,
    val url: String
)
