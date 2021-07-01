package com.ezike.tobenna.starwarssearch.character_detail.ui.views.profile

import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState

data class ProfileViewState(
    val character: CharacterDetailModel?
) : ViewState
