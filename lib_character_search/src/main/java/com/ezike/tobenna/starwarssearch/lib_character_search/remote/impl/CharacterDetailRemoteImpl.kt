package com.ezike.tobenna.starwarssearch.lib_character_search.remote.impl

import com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.remote.CharacterDetailRemote
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.ApiService
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper.CharacterDetailRemoteMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper.FilmRemoteMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper.PlanetRemoteMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.CharacterRemoteModel
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.response.FilmResponse
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.response.PlanetResponse
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.response.SpecieResponse
import javax.inject.Inject

internal class CharacterDetailRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterDetailRemoteMapper: CharacterDetailRemoteMapper,
    private val planetRemoteMapper: PlanetRemoteMapper,
    private val filmRemoteMapper: FilmRemoteMapper
) : CharacterDetailRemote {

    override suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity {
        val characterDetail: CharacterRemoteModel = apiService.fetchCharacterDetail(characterUrl)
        return characterDetailRemoteMapper.mapFromModel(characterDetail)
    }

    override suspend fun fetchPlanet(planetUrl: String): PlanetEntity {
        val planetDetails: PlanetResponse = apiService.fetchPlanet(planetUrl)
        return planetRemoteMapper.mapFromModel(planetDetails)
    }

    override suspend fun fetchSpecies(urls: List<String>): List<SpecieEntity> {
        val specieDetails: List<SpecieResponse> = urls.map { url ->
            apiService.fetchSpecieDetails(url)
        }
        val specieMap: MutableMap<String, String> = mutableMapOf()
        specieDetails.mapNotNull { specie ->
            specie.homeworld
        }.forEach { url ->
            try {
                val homeWorld: PlanetResponse = apiService.fetchPlanet(url)
                specieMap[url] = homeWorld.name
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return specieDetails.map { specie ->
            SpecieEntity(
                specie.name,
                specie.language,
                specieMap[specie.homeworld] ?: ""
            )
        }
    }

    override suspend fun fetchFilms(urls: List<String>): List<FilmEntity> {
        val filmDetails: List<FilmResponse> = urls.map { url ->
            apiService.fetchFilmDetails(url)
        }
        return filmRemoteMapper.mapModelList(filmDetails)
    }
}
