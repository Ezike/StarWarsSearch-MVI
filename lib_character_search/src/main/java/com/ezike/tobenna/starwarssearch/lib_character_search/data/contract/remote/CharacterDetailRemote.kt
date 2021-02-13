package com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.remote

import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.SpecieEntity

internal interface CharacterDetailRemote {

    suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity

    suspend fun fetchPlanet(planetUrl: String): PlanetEntity

    suspend fun fetchSpecies(urls: List<String>): List<SpecieEntity>

    suspend fun fetchFilms(urls: List<String>): List<FilmEntity>
}
