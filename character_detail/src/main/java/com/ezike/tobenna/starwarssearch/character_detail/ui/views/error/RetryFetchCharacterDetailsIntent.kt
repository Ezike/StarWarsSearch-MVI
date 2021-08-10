package com.ezike.tobenna.starwarssearch.character_detail.ui.views.error

import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent

data class RetryFetchCharacterDetailsIntent(
    val character: CharacterDetailModel
) : ViewIntent
