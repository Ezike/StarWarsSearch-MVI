package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.film

import com.ezike.tobenna.starwarssearch.characterdetail.model.FilmModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

data class FilmViewState(
    val films: List<FilmModel> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val showFilms: Boolean = false,
    val showTitle: Boolean = false,
    val showEmpty: Boolean = false
) : ViewState
