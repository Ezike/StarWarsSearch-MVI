package com.ezike.tobenna.starwarssearch.charactersearch.ui.views.history

import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

data class HistoryState(
    val data: List<CharacterModel>,
    val showHistory: Boolean
)

sealed class SearchHistoryViewState(
    val historyState: HistoryState,
    val showRecentSearchGroup: Boolean,
    val showHistoryPrompt: Boolean
) : ViewState {

    object Initial : SearchHistoryViewState(
        historyState = HistoryState(
            data = emptyList(),
            showHistory = false
        ),
        showRecentSearchGroup = false,
        showHistoryPrompt = false
    )

    data class DataLoaded(
        val data: List<CharacterModel>
    ) : SearchHistoryViewState(
        historyState = HistoryState(
            data = data,
            showHistory = data.isNotEmpty()
        ),
        showRecentSearchGroup = data.isNotEmpty(),
        showHistoryPrompt = data.isEmpty()
    )

    companion object {
        val Hide: SearchHistoryViewState
            get() = Initial
    }
}
