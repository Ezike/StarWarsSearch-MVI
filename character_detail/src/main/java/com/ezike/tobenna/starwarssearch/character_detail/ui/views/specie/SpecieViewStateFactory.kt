package com.ezike.tobenna.starwarssearch.character_detail.ui.views.specie

import com.ezike.tobenna.starwarssearch.character_search.model.SpecieModel

inline fun SpecieViewState.state(
    transform: SpecieViewStateFactory.() -> SpecieViewState
): SpecieViewState = transform(SpecieViewStateFactory(this))

object SpecieViewStateFactory {

    private lateinit var state: SpecieViewState

    operator fun invoke(viewState: SpecieViewState): SpecieViewStateFactory {
        state = viewState
        return this
    }

    val initialState: SpecieViewState
        get() = SpecieViewState()

    val Loading: SpecieViewState
        get() = state.copy(
            isLoading = true,
            showEmpty = false,
            showError = false,
            showSpecies = false,
            showTitle = true,
            errorMessage = null
        )

    val Hide: SpecieViewState
        get() = SpecieViewState()

    fun Error(message: String): SpecieViewState =
        state.copy(
            isLoading = false,
            showEmpty = false,
            showError = true,
            showSpecies = false,
            showTitle = true,
            errorMessage = message
        )

    fun DataLoaded(species: List<SpecieModel>): SpecieViewState =
        state.copy(
            species = species,
            isLoading = false,
            showTitle = true,
            showEmpty = species.isEmpty(),
            showSpecies = species.isNotEmpty(),
            showError = false,
            errorMessage = null
        )
}
