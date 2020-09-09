package com.ezike.tobenna.starwarssearch.character_search.views.detail

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.views.EmptyStateView
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

data class DetailErrorViewState private constructor(
    val errorMessage: String = "",
    val characterName: String = "",
    val isVisible: Boolean = false
) : ViewState {

    inline fun state(transform: Factory.() -> DetailErrorViewState): DetailErrorViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: DetailErrorViewState

        val init: DetailErrorViewState
            get() = DetailErrorViewState()

        val hide: DetailErrorViewState
            get() = DetailErrorViewState()

        operator fun invoke(viewState: DetailErrorViewState): Factory {
            state = viewState
            return this
        }

        fun show(errorMessage: String, characterName: String): DetailErrorViewState =
            state.copy(
                errorMessage = errorMessage,
                characterName = characterName,
                isVisible = true
            )
    }
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
