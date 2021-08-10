package com.ezike.tobenna.starwarssearch.character_detail.ui

import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent

data class LoadCharacterDetailIntent(val character: CharacterDetailModel) : ViewIntent
