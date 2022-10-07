package com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.remote

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.SpecieEntity

internal interface CharacterDetailRemote {

    suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity

    suspend fun fetchPlanet(planetUrl: String): PlanetEntity

    suspend fun fetchSpecies(urls: List<String>): List<SpecieEntity>

    suspend fun fetchFilms(urls: List<String>): List<FilmEntity>
}
