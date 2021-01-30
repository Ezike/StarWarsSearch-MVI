package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.views.detail.DetailErrorViewState
import com.ezike.tobenna.starwarssearch.character_search.views.detail.FilmViewState
import com.ezike.tobenna.starwarssearch.character_search.views.detail.PlanetViewState
import com.ezike.tobenna.starwarssearch.character_search.views.detail.SpecieViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ScreenState

data class CharacterDetailViewState private constructor(
    val character: CharacterModel? = null,
    val planetViewState: PlanetViewState = PlanetViewState.init,
    val specieViewState: SpecieViewState = SpecieViewState.init,
    val filmViewState: FilmViewState = FilmViewState.init,
    val errorViewState: DetailErrorViewState = DetailErrorViewState.init
) : ScreenState {

    inline fun translateTo(transform: Factory.() -> CharacterDetailViewState): CharacterDetailViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: CharacterDetailViewState

        operator fun invoke(viewState: CharacterDetailViewState): Factory {
            state = viewState
            return this
        }

        val init: CharacterDetailViewState
            get() = CharacterDetailViewState()

        fun profileState(character: CharacterModel?): CharacterDetailViewState =
            state.copy(character = character)

        fun planetState(action: PlanetViewState.Factory.() -> PlanetViewState): CharacterDetailViewState =
            state.copy(
                errorViewState = state.errorViewState.state { hide },
                planetViewState = state.planetViewState.state(action)
            )

        fun specieState(action: SpecieViewState.Factory.() -> SpecieViewState): CharacterDetailViewState =
            state.copy(
                errorViewState = state.errorViewState.state { hide },
                specieViewState = state.specieViewState.state(action)
            )

        fun filmState(action: FilmViewState.Factory.() -> FilmViewState): CharacterDetailViewState =
            state.copy(
                errorViewState = state.errorViewState.state { hide },
                filmViewState = state.filmViewState.state(action)
            )

        fun errorState(characterName: String, error: String): CharacterDetailViewState =
            state.copy(
                errorViewState = state.errorViewState.state { show(characterName, error) },
                planetViewState = state.planetViewState.state { hide },
                filmViewState = state.filmViewState.state { hide },
                specieViewState = state.specieViewState.state { hide }
            )

        val retryState: CharacterDetailViewState
            get() = state.copy(
                errorViewState = state.errorViewState.state { hide },
                planetViewState = state.planetViewState.state { loading },
                filmViewState = state.filmViewState.state { loading },
                specieViewState = state.specieViewState.state { loading }
            )
    }
}
