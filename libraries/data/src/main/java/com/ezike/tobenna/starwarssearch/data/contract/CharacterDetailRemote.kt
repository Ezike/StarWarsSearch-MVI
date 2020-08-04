package com.ezike.tobenna.starwarssearch.data.contract

import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity

interface CharacterDetailRemote {

    suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity

    suspend fun fetchPlanet(planetUrl: String): PlanetEntity

    suspend fun fetchSpecies(urls: List<String>): List<SpecieEntity>

    suspend fun fetchFilms(urls: List<String>): List<FilmEntity>
}
