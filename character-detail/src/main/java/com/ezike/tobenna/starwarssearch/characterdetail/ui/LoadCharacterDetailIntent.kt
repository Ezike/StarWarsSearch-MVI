package com.ezike.tobenna.starwarssearch.characterdetail.ui

import com.ezike.tobenna.starwarssearch.characterdetail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent

data class LoadCharacterDetailIntent(val character: CharacterDetailModel) : ViewIntent
