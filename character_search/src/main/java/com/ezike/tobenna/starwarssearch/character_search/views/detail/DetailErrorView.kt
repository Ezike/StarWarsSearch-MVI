package com.ezike.tobenna.starwarssearch.character_search.views.detail

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.views.EmptyStateView
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.UIComponent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

data class DetailErrorViewState(
    val errorMessage: String = "",
    val characterName: String = "",
    val isVisible: Boolean = false
) : ViewState {

    fun show(errorMessage: String, characterName: String): DetailErrorViewState =
        copy(errorMessage = errorMessage, characterName = characterName, isVisible = true)

    val hide: DetailErrorViewState
        get() = DetailErrorViewState()
}

data class RetryFetchCharacterDetailsIntent(val character: CharacterModel) : ViewIntent

class DetailErrorView(
    private val view: EmptyStateView,
    private val character: CharacterModel,
    action: DispatchIntent
) : UIComponent<DetailErrorViewState>() {

    init {
        view.onRetry { action(RetryFetchCharacterDetailsIntent(character)) }
    }

    override fun render(state: DetailErrorViewState) {
        view.isVisible = state.isVisible
        if (state.isVisible) {
            view.setCaption(state.errorMessage)
            view.setTitle(view.context.getString(R.string.error_fetching_details, character.name))
        }
    }
}
