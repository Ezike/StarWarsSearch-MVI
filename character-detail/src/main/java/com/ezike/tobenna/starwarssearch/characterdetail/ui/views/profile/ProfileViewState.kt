package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.profile

import com.ezike.tobenna.starwarssearch.core.AppString
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

data class ProfileViewState(
    val title: AppString,
    val name: AppString,
    val birthYear: AppString,
    val height: AppString
) : ViewState
