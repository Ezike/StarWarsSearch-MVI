package com.ezike.tobenna.starwarssearch.characterdetail.presentation.viewstate

import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.error.state
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.film.FilmViewState
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.film.FilmViewStateFactory
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.film.state
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.planet.PlanetViewState
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.planet.PlanetViewStateFactory
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.planet.state
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.profile.ProfileViewState
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.specie.SpecieViewState
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.specie.SpecieViewStateFactory
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.specie.state

inline fun CharacterDetailViewState.translateTo(
    transform: CharacterDetailViewStateFactory.() -> CharacterDetailViewState
): CharacterDetailViewState = transform(
    CharacterDetailViewStateFactory(this)
)

object CharacterDetailViewStateFactory {

    private lateinit var state: CharacterDetailViewState

    operator fun invoke(
        viewState: CharacterDetailViewState
    ): CharacterDetailViewStateFactory {
        state = viewState
        return this
    }

    val initialState: CharacterDetailViewState
        get() = CharacterDetailViewState()

    fun profileState(
        profileViewState: ProfileViewState
    ): CharacterDetailViewState = state.copy(
        profileViewState = profileViewState,
        planetViewState = state.planetViewState.state { Loading },
        filmViewState = state.filmViewState.state { Loading },
        specieViewState = state.specieViewState.state { Loading }
    )

    fun planetState(
        action: PlanetViewStateFactory.() -> PlanetViewState
    ): CharacterDetailViewState = state.copy(
        errorViewState = state.errorViewState.state { Hide },
        planetViewState = state.planetViewState.state(action)
    )

    fun specieState(
        action: SpecieViewStateFactory.() -> SpecieViewState
    ): CharacterDetailViewState = state.copy(
        errorViewState = state.errorViewState.state { Hide },
        specieViewState = state.specieViewState.state(action)
    )

    fun filmState(
        action: FilmViewStateFactory.() -> FilmViewState
    ): CharacterDetailViewState = state.copy(
        errorViewState = state.errorViewState.state { Hide },
        filmViewState = state.filmViewState.state(action)
    )

    fun errorState(
        characterName: String,
        error: String
    ): CharacterDetailViewState = state.copy(
        errorViewState = state.errorViewState.state { DisplayError(characterName, error) },
        planetViewState = state.planetViewState.state { Hide },
        filmViewState = state.filmViewState.state { Hide },
        specieViewState = state.specieViewState.state { Hide }
    )

    val retryState: CharacterDetailViewState
        get() = state.copy(
            errorViewState = state.errorViewState.state { Hide },
            planetViewState = state.planetViewState.state { Loading },
            filmViewState = state.filmViewState.state { Loading },
            specieViewState = state.specieViewState.state { Loading }
        )
}
