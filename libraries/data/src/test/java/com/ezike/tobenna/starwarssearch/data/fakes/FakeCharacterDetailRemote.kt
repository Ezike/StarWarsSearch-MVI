package com.ezike.tobenna.starwarssearch.data.fakes

import com.ezike.tobenna.starwarssearch.data.DummyData
import com.ezike.tobenna.starwarssearch.data.contract.remote.CharacterDetailRemote
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity

class FakeCharacterDetailRemote : CharacterDetailRemote {

    override suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity {
        return DummyData.characterDetailEntity
    }

    override suspend fun fetchPlanet(planetUrl: String): PlanetEntity {
        return DummyData.planetEntity
    }

    override suspend fun fetchSpecies(urls: List<String>): List<SpecieEntity> {
        return listOf(DummyData.specieEntity)
    }

    override suspend fun fetchFilms(urls: List<String>): List<FilmEntity> {
        return listOf(DummyData.filmEntity)
    }
}
