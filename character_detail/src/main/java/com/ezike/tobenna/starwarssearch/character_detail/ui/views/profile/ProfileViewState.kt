package com.ezike.tobenna.starwarssearch.character_detail.ui.views.profile

import com.ezike.tobenna.starwarssearch.core.AppString
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState

data class ProfileViewState(
    val title: AppString,
    val name: AppString,
    val birthYear: AppString,
    val height: AppString,
) : ViewState
