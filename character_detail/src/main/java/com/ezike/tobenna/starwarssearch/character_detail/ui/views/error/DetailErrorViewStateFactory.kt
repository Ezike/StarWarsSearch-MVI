package com.ezike.tobenna.starwarssearch.character_detail.ui.views.error

inline fun DetailErrorViewState.state(
    transform: DetailErrorViewStateFactory.() -> DetailErrorViewState
): DetailErrorViewState = transform(
    DetailErrorViewStateFactory(this)
)

object DetailErrorViewStateFactory {

    private lateinit var state: DetailErrorViewState

    val initialState: DetailErrorViewState
        get() = DetailErrorViewState()

    val Hide: DetailErrorViewState
        get() = DetailErrorViewState()

    operator fun invoke(viewState: DetailErrorViewState): DetailErrorViewStateFactory {
        state = viewState
        return this
    }

    fun DisplayError(characterName: String, errorMessage: String): DetailErrorViewState =
        state.copy(
            characterName = characterName,
            errorMessage = errorMessage,
            showError = true
        )
}
