package com.ezike.tobenna.starwarssearch.character_search.ui.views.history

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

data class SearchHistoryViewState(
    val history: List<CharacterModel> = emptyList(),
    val showHistory: Boolean = false,
    val showRecentSearchGroup: Boolean = false,
    val showHistoryPrompt: Boolean = false
) : ViewState
