package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Specie
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {

    fun getCharacterDetail(characterUrl: String): Flow<CharacterDetail>

    fun fetchPlanet(planetUrl: String): Flow<Planet>

    fun fetchSpecies(urls: List<String>): Flow<List<Specie>>

    fun fetchFilms(urls: List<String>): Flow<List<Film>>
}
