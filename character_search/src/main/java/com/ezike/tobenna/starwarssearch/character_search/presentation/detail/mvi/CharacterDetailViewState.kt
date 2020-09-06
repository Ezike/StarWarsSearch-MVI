package com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.views.detail.DetailErrorViewState
import com.ezike.tobenna.starwarssearch.character_search.views.detail.FilmViewState
import com.ezike.tobenna.starwarssearch.character_search.views.detail.PlanetViewState
import com.ezike.tobenna.starwarssearch.character_search.views.detail.SpecieViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.ScreenState

data class CharacterDetailViewState(
    val character: CharacterModel? = null,
    val planetViewState: PlanetViewState = PlanetViewState(),
    val specieViewState: SpecieViewState = SpecieViewState(),
    val filmViewState: FilmViewState = FilmViewState(),
    val errorViewState: DetailErrorViewState = DetailErrorViewState()
) : ScreenState {

    fun planetState(state: PlanetViewState.() -> PlanetViewState): CharacterDetailViewState =
        copy(
            errorViewState = errorViewState.hide,
            planetViewState = planetViewState.state()
        )

    fun specieState(state: SpecieViewState.() -> SpecieViewState): CharacterDetailViewState =
        copy(
            errorViewState = errorViewState.hide,
            specieViewState = specieViewState.state()
        )

    fun filmState(state: FilmViewState.() -> FilmViewState): CharacterDetailViewState =
        copy(
            errorViewState = errorViewState.hide,
            filmViewState = filmViewState.state()
        )

    fun errorState(characterName: String, error: String): CharacterDetailViewState =
        copy(
            errorViewState = errorViewState.show(error, characterName),
            planetViewState = planetViewState.hide,
            filmViewState = filmViewState.hide,
            specieViewState = specieViewState.hide
        )

    val retryState: CharacterDetailViewState
        get() = copy(
            errorViewState = errorViewState.hide,
            planetViewState = planetViewState.loading,
            filmViewState = filmViewState.loading,
            specieViewState = specieViewState.loading
        )
}
