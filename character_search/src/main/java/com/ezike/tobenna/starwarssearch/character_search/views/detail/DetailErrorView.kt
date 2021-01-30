package com.ezike.tobenna.starwarssearch.character_search.views.detail

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.views.EmptyStateView
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

data class RetryFetchCharacterDetailsIntent(val character: CharacterModel) : ViewIntent

class DetailErrorView(
    private val view: EmptyStateView,
    character: CharacterModel
) : UIComponent<DetailErrorViewState>() {

    init {
        view.onRetry { sendIntent(RetryFetchCharacterDetailsIntent(character)) }
    }

    override fun render(state: DetailErrorViewState) {
        view.isVisible = state.isVisible
        if (state.isVisible) {
            view.setCaption(state.errorMessage)
            view.setTitle(
                view.context.getString(
                    R.string.error_fetching_details,
                    state.characterName
                )
            )
        }
    }
}

data class DetailErrorViewState private constructor(
    val characterName: String = "",
    val errorMessage: String = "",
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

        fun show(characterName: String, errorMessage: String): DetailErrorViewState =
            state.copy(
                characterName = characterName,
                errorMessage = errorMessage,
                isVisible = true
            )
    }
}
