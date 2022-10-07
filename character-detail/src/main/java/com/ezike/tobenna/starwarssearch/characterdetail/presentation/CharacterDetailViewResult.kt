package com.ezike.tobenna.starwarssearch.characterdetail.presentation

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Specie
import com.ezike.tobenna.starwarssearch.presentation.base.ViewResult

sealed class CharacterDetailViewResult : ViewResult {
    data class CharacterDetail(val character: Character) : CharacterDetailViewResult()
    data class FetchCharacterDetailError(
        val characterName: String,
        val error: Throwable
    ) : CharacterDetailViewResult()

    object Retrying : CharacterDetailViewResult()
}

sealed class PlanetDetailViewResult : CharacterDetailViewResult() {
    data class Success(val planet: Planet) : PlanetDetailViewResult()
    data class Error(val error: Throwable) : PlanetDetailViewResult()
    object Loading : PlanetDetailViewResult()
}

sealed class SpecieDetailViewResult : CharacterDetailViewResult() {
    data class Success(val specie: List<Specie>) : SpecieDetailViewResult()
    data class Error(val error: Throwable) : SpecieDetailViewResult()
    object Loading : SpecieDetailViewResult()
}

sealed class FilmDetailViewResult : CharacterDetailViewResult() {
    data class Success(val film: List<Film>) : FilmDetailViewResult()
    data class Error(val error: Throwable) : FilmDetailViewResult()
    object Loading : FilmDetailViewResult()
}
