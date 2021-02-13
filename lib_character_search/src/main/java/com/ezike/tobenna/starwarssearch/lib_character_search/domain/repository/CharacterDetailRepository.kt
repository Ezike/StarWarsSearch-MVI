package com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Film
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Planet
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Specie
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {

    fun getCharacterDetail(characterUrl: String): Flow<CharacterDetail>

    fun fetchPlanet(planetUrl: String): Flow<Planet>

    fun fetchSpecies(urls: List<String>): Flow<List<Specie>>

    fun fetchFilms(urls: List<String>): Flow<List<Film>>
}
