package com.ezike.tobenna.starwarssearch.character_detail.ui.views.planet

import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent

data class RetryFetchPlanetIntent(
    val url: String
) : ViewIntent
