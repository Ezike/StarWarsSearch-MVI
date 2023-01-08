package com.ezike.tobenna.starwarssearch.characterdetail.data

import com.ezike.tobenna.starwarssearch.cache.model.CharacterDetailCacheModel
import com.ezike.tobenna.starwarssearch.cache.room.CharacterDetailDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal interface CharacterDetailRepository {
    fun getCharacterDetail(characterUrl: String): Flow<CharacterDetailEntity>
    suspend fun fetchPlanet(planetUrl: String): PlanetEntity
    suspend fun fetchSpecies(urls: List<String>): List<SpecieEntity>
    suspend fun fetchFilms(urls: List<String>): List<FilmEntity>
}

internal class CharacterDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterDetailDao: CharacterDetailDao,
) : CharacterDetailRepository {

    override fun getCharacterDetail(characterUrl: String): Flow<CharacterDetailEntity> =
        flow {
            val storedCharacter =
                characterDetailDao.fetchCharacter(characterUrl)
            if (storedCharacter != null) {
                val cachedCharacter = CharacterDetailEntity(
                    storedCharacter.filmUrls,
                    storedCharacter.planetUrl,
                    storedCharacter.speciesUrls,
                    storedCharacter.url
                )
                emit(cachedCharacter)
            } else {
                val character = apiService.fetchCharacterDetail(characterUrl)
                val characterDetail = CharacterDetailEntity(
                    character.films,
                    character.homeworld,
                    character.species,
                    character.url
                )
                emit(characterDetail)
                val model = CharacterDetailCacheModel(
                    characterDetail.filmUrls,
                    characterDetail.planetUrl,
                    characterDetail.speciesUrls,
                    characterDetail.url
                )
                characterDetailDao.insertCharacter(model)
            }
        }

    override suspend fun fetchPlanet(planetUrl: String): PlanetEntity =
        apiService.fetchPlanet(planetUrl)

    override suspend fun fetchSpecies(urls: List<String>): List<SpecieEntity> {
        val specieDetails = urls.map { url ->
            apiService.fetchSpecieDetails(url)
        }
        val specieMap: MutableMap<String, String> = mutableMapOf()
        specieDetails.mapNotNull { specie ->
            specie.homeworld
        }.forEach { url ->
            try {
                val homeWorld = apiService.fetchPlanet(url)
                specieMap[url] = homeWorld.name
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return specieDetails.map { specie ->
            specie.copy(
                homeworld = specieMap.getOrDefault(key = specie.homeworld, defaultValue = "")
            )
        }
    }

    override suspend fun fetchFilms(urls: List<String>): List<FilmEntity> =
        urls.map { url -> apiService.fetchFilmDetails(url) }
}
