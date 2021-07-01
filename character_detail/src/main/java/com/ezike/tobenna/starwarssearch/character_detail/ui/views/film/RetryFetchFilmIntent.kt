package com.ezike.tobenna.starwarssearch.character_detail.ui.views.film

import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewIntent

data class RetryFetchFilmIntent(
    val url: String
) : ViewIntent
