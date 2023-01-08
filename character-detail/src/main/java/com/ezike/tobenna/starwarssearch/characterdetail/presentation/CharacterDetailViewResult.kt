package com.ezike.tobenna.starwarssearch.characterdetail.presentation

import com.ezike.tobenna.starwarssearch.characterdetail.data.FilmEntity
import com.ezike.tobenna.starwarssearch.characterdetail.data.PlanetEntity
import com.ezike.tobenna.starwarssearch.characterdetail.data.SpecieEntity
import com.ezike.tobenna.starwarssearch.characterdetail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewResult

internal sealed class CharacterDetailViewResult : ViewResult {
    data class CharacterDetail(val character: CharacterDetailModel) : CharacterDetailViewResult()
    data class FetchCharacterDetailError(
        val characterName: String,
        val error: Throwable
    ) : CharacterDetailViewResult()

    object Retrying : CharacterDetailViewResult()
}

internal sealed class PlanetDetailViewResult : CharacterDetailViewResult() {
    data class Success(val planet: PlanetEntity) : PlanetDetailViewResult()
    data class Error(val error: Throwable) : PlanetDetailViewResult()
    object Loading : PlanetDetailViewResult()
}

internal sealed class SpecieDetailViewResult : CharacterDetailViewResult() {
    data class Success(val specie: List<SpecieEntity>) : SpecieDetailViewResult()
    data class Error(val error: Throwable) : SpecieDetailViewResult()
    object Loading : SpecieDetailViewResult()
}

internal sealed class FilmDetailViewResult : CharacterDetailViewResult() {
    data class Success(val film: List<FilmEntity>) : FilmDetailViewResult()
    data class Error(val error: Throwable) : FilmDetailViewResult()
    object Loading : FilmDetailViewResult()
}
