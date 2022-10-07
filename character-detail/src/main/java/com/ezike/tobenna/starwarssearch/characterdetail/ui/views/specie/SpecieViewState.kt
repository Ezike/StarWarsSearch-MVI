package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.specie

import com.ezike.tobenna.starwarssearch.characterdetail.model.SpecieModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

data class SpecieViewState(
    val species: List<SpecieModel> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val showEmpty: Boolean = false,
    val showError: Boolean = false,
    val showTitle: Boolean = false,
    val showSpecies: Boolean = false
) : ViewState
