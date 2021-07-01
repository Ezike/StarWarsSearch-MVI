package com.ezike.tobenna.starwarssearch.character_detail.ui.views.specie

import com.ezike.tobenna.starwarssearch.character_search.model.SpecieModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState

data class SpecieViewState(
    val species: List<SpecieModel> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val showEmpty: Boolean = false,
    val showError: Boolean = false,
    val showTitle: Boolean = false,
    val showSpecies: Boolean = false
) : ViewState
