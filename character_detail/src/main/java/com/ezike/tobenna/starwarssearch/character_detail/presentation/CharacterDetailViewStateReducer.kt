package com.ezike.tobenna.starwarssearch.character_detail.presentation

import com.ezike.tobenna.starwarssearch.character_detail.mapper.CharacterDetailModelMapper
import com.ezike.tobenna.starwarssearch.character_detail.mapper.FilmModelMapper
import com.ezike.tobenna.starwarssearch.character_detail.mapper.PlanetModelMapper
import com.ezike.tobenna.starwarssearch.character_detail.mapper.SpecieModelMapper
import com.ezike.tobenna.starwarssearch.character_detail.presentation.CharacterDetailViewResult.CharacterDetail
import com.ezike.tobenna.starwarssearch.character_detail.presentation.CharacterDetailViewResult.FetchCharacterDetailError
import com.ezike.tobenna.starwarssearch.character_detail.presentation.viewstate.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_detail.presentation.viewstate.translateTo
import com.ezike.tobenna.starwarssearch.core.ext.errorMessage
import javax.inject.Inject

class CharacterDetailViewStateReducer @Inject constructor(
    private val planetModelMapper: PlanetModelMapper,
    private val specieModelMapper: SpecieModelMapper,
    private val filmModelMapper: FilmModelMapper,
    private val characterModelMapper: CharacterDetailModelMapper
) : CharacterDetailStateReducer {

    override fun reduce(
        oldState: CharacterDetailViewState,
        result: CharacterDetailViewResult
    ): CharacterDetailViewState {
        return when (result) {
            is CharacterDetail -> oldState.translateTo {
                initialState(characterModelMapper.mapToModel(result.character))
            }
            is FetchCharacterDetailError -> oldState.translateTo {
                errorState(
                    characterName = result.characterName,
                    error = result.error.errorMessage
                )
            }
            CharacterDetailViewResult.Retrying -> oldState.translateTo { retryState }
            is PlanetDetailViewResult -> makePlanetState(result, oldState)
            is SpecieDetailViewResult -> makeSpecieState(result, oldState)
            is FilmDetailViewResult -> makeFilmState(result, oldState)
        }
    }

    private fun makeFilmState(
        result: FilmDetailViewResult,
        previous: CharacterDetailViewState
    ): CharacterDetailViewState {
        return when (result) {
            is FilmDetailViewResult.Success ->
                previous.translateTo {
                    filmState { success(filmModelMapper.mapToModelList(result.film)) }
                }
            is FilmDetailViewResult.Error ->
                previous.translateTo { filmState { error(result.error.errorMessage) } }
            FilmDetailViewResult.Loading -> previous.translateTo { filmState { loading } }
        }
    }

    private fun makeSpecieState(
        result: SpecieDetailViewResult,
        previous: CharacterDetailViewState
    ): CharacterDetailViewState {
        return when (result) {
            is SpecieDetailViewResult.Success ->
                previous.translateTo {
                    specieState { DataLoaded(specieModelMapper.mapToModelList(result.specie)) }
                }
            is SpecieDetailViewResult.Error ->
                previous.translateTo { specieState { Error(result.error.errorMessage) } }
            SpecieDetailViewResult.Loading -> previous.translateTo { specieState { Loading } }
        }
    }

    private fun makePlanetState(
        result: PlanetDetailViewResult,
        previous: CharacterDetailViewState
    ): CharacterDetailViewState {
        return when (result) {
            is PlanetDetailViewResult.Success ->
                previous.translateTo {
                    planetState { Success(planetModelMapper.mapToModel(result.planet)) }
                }
            is PlanetDetailViewResult.Error ->
                previous.translateTo { planetState { Error(result.error.errorMessage) } }
            PlanetDetailViewResult.Loading -> previous.translateTo { planetState { Loading } }
        }
    }
}
