package com.ezike.tobenna.starwarssearch.libcharactersearch.data.fakes

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.DummyData
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.remote.CharacterDetailRemote
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.SpecieEntity

internal class FakeCharacterDetailRemote : CharacterDetailRemote {

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
