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
            is PlanetDetailViewResult -> planetDetailViewState(result)
            is SpecieDetailViewResult -> specieDetailViewState(result)
            is FilmDetailViewResult -> filmDetailViewState(result)
            CharacterDetailViewResult.Idle -> CharacterDetailViewState.Idle
            is CharacterDetailViewResult.CharacterDetail -> {
                CharacterDetailViewState
                    .ProfileLoaded(characterModelMapper.mapToModel(result.character))
            }
            is CharacterDetailViewResult.FetchCharacterDetailError ->
                CharacterDetailViewState.FetchDetailError(result.error.errorMessage)
        }
    }

    private fun filmDetailViewState(result: FilmDetailViewResult): FilmDetailViewState {
        return when (result) {
            is FilmDetailViewResult.Success -> {
                FilmDetailViewState.Success(filmModelMapper.mapToModelList(result.film))
            }
            is FilmDetailViewResult.Error -> FilmDetailViewState.Error(result.error.errorMessage)
            FilmDetailViewResult.Loading -> FilmDetailViewState.Loading
        }
    }

    private fun specieDetailViewState(result: SpecieDetailViewResult): SpecieDetailViewState {
        return when (result) {
            is SpecieDetailViewResult.Success -> {
                SpecieDetailViewState.Success(specieModelMapper.mapToModelList(result.specie))
            }
            is SpecieDetailViewResult.Error ->
                SpecieDetailViewState.Error(result.error.errorMessage)
            SpecieDetailViewResult.Loading -> SpecieDetailViewState.Loading
        }
    }

    private fun planetDetailViewState(result: PlanetDetailViewResult): PlanetDetailViewState {
        return when (result) {
            is PlanetDetailViewResult.Success -> {
                PlanetDetailViewState.Success(planetModelMapper.mapToModel(result.planet))
            }
            is PlanetDetailViewResult.Error ->
                PlanetDetailViewState.Error(result.error.errorMessage)
            PlanetDetailViewResult.Loading -> PlanetDetailViewState.Loading
        }
    }
}
