package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.error

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character.detail.R
import com.ezike.tobenna.starwarssearch.characterdetail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.core.EmptyStateView
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class DetailErrorView(
    private val view: EmptyStateView,
    character: CharacterDetailModel
) : UIComponent<DetailErrorViewState>() {

    init {
        view.onRetry {
            sendIntent(RetryFetchCharacterDetailsIntent(character))
        }
    }

    override fun render(state: DetailErrorViewState) {
        view.run {
            isVisible = state.showError
            setCaption(state.errorMessage)
            setTitle(
                context.getString(
                    R.string.error_fetching_details,
                    state.characterName
                )
            )
        }
    }
}
