package com.ezike.tobenna.starwarssearch.character_detail.presentation

import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.character_detail.views.DetailErrorViewState
import com.ezike.tobenna.starwarssearch.character_detail.views.FilmViewState
import com.ezike.tobenna.starwarssearch.character_detail.views.PlanetViewState
import com.ezike.tobenna.starwarssearch.character_detail.views.SpecieViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ScreenState

data class CharacterDetailViewState private constructor(
    val character: CharacterDetailModel? = null,
    val planetViewState: PlanetViewState = PlanetViewState.Init,
    val specieViewState: SpecieViewState = SpecieViewState.Init,
    val filmViewState: FilmViewState = FilmViewState.init,
    val errorViewState: DetailErrorViewState = DetailErrorViewState.Init
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

        fun initialState(character: CharacterDetailModel?): CharacterDetailViewState =
            state.copy(
                character = character,
                planetViewState = state.planetViewState.state { Loading },
                filmViewState = state.filmViewState.state { loading },
                specieViewState = state.specieViewState.state { Loading }
            )

        fun planetState(action: PlanetViewState.Factory.() -> PlanetViewState): CharacterDetailViewState =
            state.copy(
                errorViewState = state.errorViewState.state { Hide },
                planetViewState = state.planetViewState.state(action)
            )

        fun specieState(action: SpecieViewState.Factory.() -> SpecieViewState): CharacterDetailViewState =
            state.copy(
                errorViewState = state.errorViewState.state { Hide },
                specieViewState = state.specieViewState.state(action)
            )

        fun filmState(action: FilmViewState.Factory.() -> FilmViewState): CharacterDetailViewState =
            state.copy(
                errorViewState = state.errorViewState.state { Hide },
                filmViewState = state.filmViewState.state(action)
            )

        fun errorState(characterName: String, error: String): CharacterDetailViewState =
            state.copy(
                errorViewState = state.errorViewState.state { DisplayError(characterName, error) },
                planetViewState = state.planetViewState.state { Hide },
                filmViewState = state.filmViewState.state { hide },
                specieViewState = state.specieViewState.state { Hide }
            )

        val retryState: CharacterDetailViewState
            get() = state.copy(
                errorViewState = state.errorViewState.state { Hide },
                planetViewState = state.planetViewState.state { Loading },
                filmViewState = state.filmViewState.state { loading },
                specieViewState = state.specieViewState.state { Loading }
            )
    }
}
