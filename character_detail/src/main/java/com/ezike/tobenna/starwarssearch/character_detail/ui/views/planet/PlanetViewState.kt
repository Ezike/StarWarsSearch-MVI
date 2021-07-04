package com.ezike.tobenna.starwarssearch.character_detail.ui.views.planet

import com.ezike.tobenna.starwarssearch.core.AppString
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState

data class PlanetViewState(
    val data: PlanetViewData,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val showPlanet: Boolean = false,
    val showTitle: Boolean = false
) : ViewState

data class PlanetViewData(
    val name: AppString,
    val population: AppString
)
