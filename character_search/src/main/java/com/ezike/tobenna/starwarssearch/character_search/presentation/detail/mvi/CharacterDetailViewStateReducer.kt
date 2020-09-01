package com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi

import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.FilmModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.PlanetModelMapper
import com.ezike.tobenna.starwarssearch.character_search.mapper.SpecieModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailStateReducer
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import javax.inject.Inject

class CharacterDetailViewStateReducer @Inject constructor(
    private val planetModelMapper: PlanetModelMapper,
    private val specieModelMapper: SpecieModelMapper,
    private val filmModelMapper: FilmModelMapper,
    private val characterModelMapper: CharacterModelMapper
) : CharacterDetailStateReducer {

    override fun reduce(
        previous: CharacterDetailViewState,
        result: CharacterDetailViewResult
    ): CharacterDetailViewState {
        return when (result) {
            is CharacterDetailViewResult.CharacterDetail ->
                previous.copy(character = characterModelMapper.mapToModel(result.character))
            is CharacterDetailViewResult.FetchCharacterDetailError ->
                previous.errorState(result.characterName, result.error.errorMessage)
            CharacterDetailViewResult.Retrying -> previous.retry
            is PlanetDetailViewResult -> makePlanetState(result, previous)
            is SpecieDetailViewResult -> makeSpecieState(result, previous)
            is FilmDetailViewResult -> makeFilmState(result, previous)
        }
    }

    private fun makeFilmState(
        result: FilmDetailViewResult,
        previous: CharacterDetailViewState
    ): CharacterDetailViewState {
        return when (result) {
            is FilmDetailViewResult.Success ->
                previous.filmState { success(filmModelMapper.mapToModelList(result.film)) }
            is FilmDetailViewResult.Error ->
                previous.filmState { error(result.error.errorMessage) }
            FilmDetailViewResult.Loading -> previous.filmState { loading }
        }
    }

    private fun makeSpecieState(
        result: SpecieDetailViewResult,
        previous: CharacterDetailViewState
    ): CharacterDetailViewState {
        return when (result) {
            is SpecieDetailViewResult.Success ->
                previous.specieState { success(specieModelMapper.mapToModelList(result.specie)) }
            is SpecieDetailViewResult.Error ->
                previous.specieState { error(result.error.errorMessage) }
            SpecieDetailViewResult.Loading -> previous.specieState { loading }
        }
    }

    private fun makePlanetState(
        result: PlanetDetailViewResult,
        previous: CharacterDetailViewState
    ): CharacterDetailViewState {
        return when (result) {
            is PlanetDetailViewResult.Success ->
                previous.planetState { success(planetModelMapper.mapToModel(result.planet)) }
            is PlanetDetailViewResult.Error ->
                previous.planetState { error(result.error.errorMessage) }
            PlanetDetailViewResult.Loading -> previous.planetState { loading }
        }
    }
}
