package com.ezike.tobenna.starwarssearch.character_search.views.detail

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.views.EmptyStateView
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent
import com.ezike.tobenna.starwarssearch.presentation_android.UIRenderer

data class RetryFetchCharacterDetailsIntent(val character: CharacterModel) : ViewIntent

@Suppress("FunctionName")
fun DetailErrorView(
    view: EmptyStateView,
    character: CharacterModel,
    action: DispatchIntent
): UIComponent<DetailErrorViewState> {

    view.onRetry { action(RetryFetchCharacterDetailsIntent(character)) }

    return UIRenderer { (errorMessage: String, isVisible: Boolean) ->
        view.isVisible = isVisible
        if (isVisible) {
            view.setCaption(errorMessage)
            view.setTitle(view.context.getString(R.string.error_fetching_details, character.name))
        }
    }
}

data class DetailErrorViewState private constructor(
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

        fun show(errorMessage: String): DetailErrorViewState =
            state.copy(
                errorMessage = errorMessage,
                isVisible = true
            )
    }
}
