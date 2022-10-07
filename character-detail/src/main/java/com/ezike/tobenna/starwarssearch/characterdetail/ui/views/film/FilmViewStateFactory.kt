package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.film

import com.ezike.tobenna.starwarssearch.characterdetail.model.FilmModel

inline fun FilmViewState.state(
    transform: FilmViewStateFactory.() -> FilmViewState
): FilmViewState = transform(FilmViewStateFactory(this))

object FilmViewStateFactory {

    private lateinit var state: FilmViewState

    operator fun invoke(viewState: FilmViewState): FilmViewStateFactory {
        state = viewState
        return this
    }

    val initialState: FilmViewState
        get() = FilmViewState()

    val Loading: FilmViewState
        get() = state.copy(
            films = emptyList(),
            errorMessage = null,
            isLoading = true,
            showError = false,
            showFilms = false,
            showEmpty = false,
            showTitle = true
        )

    val Hide: FilmViewState
        get() = FilmViewState()

    fun Error(message: String): FilmViewState =
        state.copy(
            films = emptyList(),
            errorMessage = message,
            isLoading = false,
            showError = true,
            showFilms = false,
            showEmpty = false,
            showTitle = true
        )

    fun Success(films: List<FilmModel>): FilmViewState =
        state.copy(
            films = films,
            errorMessage = null,
            isLoading = false,
            showError = false,
            showTitle = true,
            showFilms = films.isNotEmpty(),
            showEmpty = films.isEmpty()
        )
}
