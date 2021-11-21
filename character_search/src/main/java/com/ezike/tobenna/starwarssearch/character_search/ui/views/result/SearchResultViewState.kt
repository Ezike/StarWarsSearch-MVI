package com.ezike.tobenna.starwarssearch.character_search.ui.views.result

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

data class ResultState(
    val data: List<CharacterModel>,
    val showResult: Boolean
)

data class ErrorState(
    val showError: Boolean,
    val error: String?
)

sealed class SearchResultViewState(
    val resultState: ResultState,
    val showProgress: Boolean,
    val showEmpty: Boolean,
    val errorState: ErrorState
) : ViewState {

    object Initial : SearchResultViewState(
        resultState = ResultState(
            data = emptyList(),
            showResult = false
        ),
        showProgress = false,
        showEmpty = false,
        errorState = ErrorState(
            showError = false,
            error = null
        )
    )

    data class Searching(
        val data: List<CharacterModel>
    ) : SearchResultViewState(
        resultState = ResultState(
            data = data,
            showResult = data.isNotEmpty()
        ),
        showProgress = data.isEmpty(),
        showEmpty = false,
        errorState = ErrorState(
            showError = false,
            error = null
        )
    )

    data class Error(
        val message: String
    ) : SearchResultViewState(
        resultState = ResultState(
            data = emptyList(),
            showResult = false
        ),
        showProgress = false,
        showEmpty = false,
        errorState = ErrorState(
            showError = true,
            error = message
        )
    )

    data class DataLoaded(
        val data: List<CharacterModel>
    ) : SearchResultViewState(
        resultState = ResultState(
            data = data,
            showResult = data.isNotEmpty()
        ),
        showProgress = false,
        showEmpty = data.isEmpty(),
        errorState = ErrorState(
            showError = false,
            error = null
        )
    )

    companion object {
        val Hide: SearchResultViewState
            get() = Initial
    }
}
