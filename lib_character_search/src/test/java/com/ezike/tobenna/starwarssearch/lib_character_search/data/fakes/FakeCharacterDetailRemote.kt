package com.ezike.tobenna.starwarssearch.lib_character_search.data.fakes

import com.ezike.tobenna.starwarssearch.lib_character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.remote.CharacterDetailRemote
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.SpecieEntity

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
