package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.error

import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

data class DetailErrorViewState(
    val characterName: String = "",
    val errorMessage: String = "",
    val showError: Boolean = false
) : ViewState
