package com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.model.FilmModel
import com.ezike.tobenna.starwarssearch.character_search.model.PlanetModel
import com.ezike.tobenna.starwarssearch.character_search.model.SpecieModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

sealed class CharacterDetailViewState : ViewState {
    object Idle : CharacterDetailViewState()
    data class ProfileLoaded(val character: CharacterModel) : CharacterDetailViewState()
    data class FetchDetailError(val message: String) : CharacterDetailViewState()
}

sealed class PlanetDetailViewState : CharacterDetailViewState() {
    data class Success(val planet: PlanetModel) : PlanetDetailViewState()
    data class Error(val message: String) : PlanetDetailViewState()
    object Loading : PlanetDetailViewState()
}

sealed class SpecieDetailViewState : CharacterDetailViewState() {
    data class Success(val specie: List<SpecieModel>) : SpecieDetailViewState()
    data class Error(val message: String) : SpecieDetailViewState()
    object Loading : SpecieDetailViewState()
}

sealed class FilmDetailViewState : CharacterDetailViewState() {
    data class Success(val films: List<FilmModel>) : FilmDetailViewState()
    data class Error(val message: String) : FilmDetailViewState()
    object Loading : FilmDetailViewState()
}
