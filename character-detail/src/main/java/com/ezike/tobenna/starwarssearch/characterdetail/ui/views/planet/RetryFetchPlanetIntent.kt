package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.planet

import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent

data class RetryFetchPlanetIntent(
    val url: String
) : ViewIntent
