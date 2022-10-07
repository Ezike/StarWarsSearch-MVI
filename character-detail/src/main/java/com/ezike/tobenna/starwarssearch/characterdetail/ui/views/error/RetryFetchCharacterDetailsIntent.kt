package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.error

import com.ezike.tobenna.starwarssearch.characterdetail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent

data class RetryFetchCharacterDetailsIntent(
    val character: CharacterDetailModel
) : ViewIntent
