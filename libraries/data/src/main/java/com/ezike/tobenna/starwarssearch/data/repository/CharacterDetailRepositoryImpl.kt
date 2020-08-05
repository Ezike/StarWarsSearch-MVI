package com.ezike.tobenna.starwarssearch.data.repository

import com.ezike.tobenna.starwarssearch.data.contract.cache.CharacterDetailCache
import com.ezike.tobenna.starwarssearch.data.contract.remote.CharacterDetailRemote
import com.ezike.tobenna.starwarssearch.data.mapper.CharacterDetailEntityMapper
import com.ezike.tobenna.starwarssearch.data.mapper.FilmEntityMapper
import com.ezike.tobenna.starwarssearch.data.mapper.PlanetEntityMapper
import com.ezike.tobenna.starwarssearch.data.mapper.SpeciesEntityMapper
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.domain.model.Specie
import com.ezike.tobenna.starwarssearch.domain.repository.CharacterDetailRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterDetailRepositoryImpl @Inject constructor(
    private val characterDetailRemote: CharacterDetailRemote,
    private val characterDetailCache: CharacterDetailCache,
    private val characterDetailEntityMapper: CharacterDetailEntityMapper,
    private val planetEntityMapper: PlanetEntityMapper,
    private val filmEntityMapper: FilmEntityMapper,
    private val speciesEntityMapper: SpeciesEntityMapper
) : CharacterDetailRepository {

    override fun getCharacterDetail(characterUrl: String): Flow<CharacterDetail> {
        return flow {
            val cachedCharacter: CharacterDetailEntity? =
                characterDetailCache.fetchCharacter(characterUrl)
            if (cachedCharacter != null) {
                emit(characterDetailEntityMapper.mapFromEntity(cachedCharacter))
            } else {
                val characterDetail: CharacterDetailEntity =
                    characterDetailRemote.fetchCharacter(characterUrl)
                emit(characterDetailEntityMapper.mapFromEntity(characterDetail))
                characterDetailCache.saveCharacter(characterDetail)
            }
        }
    }

    override fun fetchPlanet(planetUrl: String): Flow<Planet> {
        return flow {
            val planet: PlanetEntity = characterDetailRemote.fetchPlanet(planetUrl)
            emit(planetEntityMapper.mapFromEntity(planet))
        }
    }

    override fun fetchSpecies(urls: List<String>): Flow<List<Specie>> {
        return flow {
            val species: List<SpecieEntity> = characterDetailRemote.fetchSpecies(urls)
            emit(speciesEntityMapper.mapFromEntityList(species))
        }
    }

    override fun fetchFilms(urls: List<String>): Flow<List<Film>> {
        return flow {
            val films: List<FilmEntity> = characterDetailRemote.fetchFilms(urls)
            emit(filmEntityMapper.mapFromEntityList(films))
        }
    }
}
