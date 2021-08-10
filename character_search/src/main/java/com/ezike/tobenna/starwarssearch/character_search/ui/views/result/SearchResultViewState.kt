package com.ezike.tobenna.starwarssearch.character_search.ui.views.result

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

data class SearchResultViewState(
    val searchResult: List<CharacterModel> = emptyList(),
    val showProgress: Boolean = false,
    val showEmpty: Boolean = false,
    val showResult: Boolean = false,
    val showError: Boolean = false,
    val error: String? = null
) : ViewState
