package com.ezike.tobenna.starwarssearch.remote.remote

import com.ezike.tobenna.starwarssearch.data.contract.CharacterDetailRemote
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.remote.ApiService
import com.ezike.tobenna.starwarssearch.remote.mapper.CharacterDetailRemoteMapper
import com.ezike.tobenna.starwarssearch.remote.mapper.FilmRemoteMapper
import com.ezike.tobenna.starwarssearch.remote.mapper.PlanetRemoteMapper
import com.ezike.tobenna.starwarssearch.remote.mapper.SpecieRemoteMapper
import com.ezike.tobenna.starwarssearch.remote.model.CharacterRemoteModel
import com.ezike.tobenna.starwarssearch.remote.model.response.FilmResponse
import com.ezike.tobenna.starwarssearch.remote.model.response.PlanetResponse
import com.ezike.tobenna.starwarssearch.remote.model.response.SpecieResponse
import javax.inject.Inject

class CharacterDetailRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterDetailRemoteMapper: CharacterDetailRemoteMapper,
    private val planetRemoteMapper: PlanetRemoteMapper,
    private val specieRemoteMapper: SpecieRemoteMapper,
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
        return specieRemoteMapper.mapModelList(specieDetails)
    }

    override suspend fun fetchFilms(urls: List<String>): List<FilmEntity> {
        val filmDetails: List<FilmResponse> = urls.map { url ->
            apiService.fetchFilmDetails(url)
        }
        return filmRemoteMapper.mapModelList(filmDetails)
    }
}
