package com.ezike.tobenna.starwarssearch.character_detail.presentation.viewstate

import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.error.DetailErrorViewState
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.error.DetailErrorViewStateFactory
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.film.FilmViewState
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.film.FilmViewStateFactory
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.planet.PlanetViewState
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.planet.PlanetViewStateFactory
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.specie.SpecieViewState
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.specie.SpecieViewStateFactory
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ScreenState

data class CharacterDetailViewState(
    val character: CharacterDetailModel? = null,
    val planetViewState: PlanetViewState = PlanetViewStateFactory.initialState,
    val specieViewState: SpecieViewState = SpecieViewStateFactory.initialState,
    val filmViewState: FilmViewState = FilmViewStateFactory.initialState,
    val errorViewState: DetailErrorViewState = DetailErrorViewStateFactory.initialState
) : ScreenState
