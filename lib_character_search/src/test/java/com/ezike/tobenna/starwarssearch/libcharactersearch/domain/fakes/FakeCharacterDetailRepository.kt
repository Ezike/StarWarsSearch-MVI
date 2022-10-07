package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.fakes

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Specie
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.CharacterDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.net.SocketTimeoutException

internal class FakeCharacterDetailRepository : CharacterDetailRepository {

    private var charactersFlow: Flow<CharacterDetail> = flowOf(DummyData.characterDetail)
    private var planetFlow: Flow<Planet> = flowOf(DummyData.planet)
    private var specieFlow: Flow<List<Specie>> = flowOf(listOf(DummyData.specie))
    private var filmFlow: Flow<List<Film>> = flowOf(listOf(DummyData.film))

    var characterResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            charactersFlow = makeCharacterResponse(value)
        }

    var planetResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            planetFlow = makePlanetResponse(value)
        }

    var filmResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            filmFlow = makeFilmResponse(value)
        }

    var specieResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            specieFlow = makeSpecieResponse(value)
        }

    private fun makeCharacterResponse(type: ResponseType): Flow<CharacterDetail> {
        return when (type) {
            ResponseType.DATA -> flowOf(DummyData.characterDetail)
            ResponseType.EMPTY -> flowOf()
            ResponseType.ERROR -> flow { throw SocketTimeoutException(FakeSearchRepository.ERROR_MSG) }
        }
    }

    private fun makePlanetResponse(type: ResponseType): Flow<Planet> {
        return when (type) {
            ResponseType.DATA -> flowOf(DummyData.planet)
            ResponseType.EMPTY -> flowOf()
            ResponseType.ERROR -> flow { throw SocketTimeoutException(FakeSearchRepository.ERROR_MSG) }
        }
    }

    private fun makeFilmResponse(type: ResponseType): Flow<List<Film>> {
        return when (type) {
            ResponseType.DATA -> flowOf(listOf(DummyData.film))
            ResponseType.EMPTY -> flowOf()
            ResponseType.ERROR -> flow { throw SocketTimeoutException(FakeSearchRepository.ERROR_MSG) }
        }
    }

    private fun makeSpecieResponse(type: ResponseType): Flow<List<Specie>> {
        return when (type) {
            ResponseType.DATA -> flowOf(listOf(DummyData.specie))
            ResponseType.EMPTY -> flowOf()
            ResponseType.ERROR -> flow { throw SocketTimeoutException(FakeSearchRepository.ERROR_MSG) }
        }
    }

    override fun getCharacterDetail(characterUrl: String): Flow<CharacterDetail> {
        return charactersFlow
    }

    override fun fetchPlanet(planetUrl: String): Flow<Planet> {
        return planetFlow
    }

    override fun fetchSpecies(urls: List<String>): Flow<List<Specie>> {
        return specieFlow
    }

    override fun fetchFilms(urls: List<String>): Flow<List<Film>> {
        return filmFlow
    }
}
