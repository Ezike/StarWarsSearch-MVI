package com.ezike.tobenna.starwarssearch.character_detail.ui.views.specie

import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewIntent

data class RetryFetchSpecieIntent(
    val url: String
) : ViewIntent
