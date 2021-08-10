package com.ezike.tobenna.starwarssearch.character_detail.ui.views.error

import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

data class DetailErrorViewState(
    val characterName: String = "",
    val errorMessage: String = "",
    val showError: Boolean = false
) : ViewState
