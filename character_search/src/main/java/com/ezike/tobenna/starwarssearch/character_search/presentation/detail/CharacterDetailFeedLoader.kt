package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.FilmDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.PlanetDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.SpecieDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.DetailAdapterModel
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.DetailAdapterModel.EmptyView
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.DetailAdapterModel.ErrorView
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.DetailAdapterModel.FilmView
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.DetailAdapterModel.Header
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.DetailAdapterModel.LoadingView
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.DetailAdapterModel.PlanetView
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.DetailAdapterModel.ProfileView
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.DetailAdapterModel.SpecieView

@OptIn(ExperimentalStdlibApi::class)
fun generateDetailFeed(state: CharacterDetailViewState): List<DetailAdapterModel> {
    return buildList {
        when (state) {
            is PlanetDetailViewState -> {
                when (state) {
                    is PlanetDetailViewState.Success -> {
                        add(Header(R.string.planet))
                        add(PlanetView(state.planet))
                    }
                    is PlanetDetailViewState.Error -> add(ErrorView(state.message, R.string.planet))
                    PlanetDetailViewState.Loading -> add(LoadingView)
                    else -> {
                    }
                }
            }
            is SpecieDetailViewState -> {
                when (state) {
                    is SpecieDetailViewState.Success -> {
                        add(Header(R.string.species))
                        if (state.specie.isEmpty()) {
                            add(EmptyView(R.string.species))
                        } else {
                            addAll(state.specie.map { SpecieView(it) })
                        }
                    }
                    is SpecieDetailViewState.Error -> add(
                        ErrorView(state.message, R.string.species)
                    )
                    SpecieDetailViewState.Loading -> add(LoadingView)
                    else -> {
                    }
                }
            }
            is FilmDetailViewState -> {
                when (state) {
                    is FilmDetailViewState.Success -> {
                        add(Header(R.string.films))
                        if (state.films.isEmpty()) {
                            add(EmptyView(R.string.films))
                        } else {
                            addAll(state.films.map { FilmView(it) })
                        }
                    }
                    is FilmDetailViewState.Error -> add(ErrorView(state.message, R.string.films))
                    FilmDetailViewState.Loading -> add(LoadingView)
                    else -> {
                    }
                }
            }
            CharacterDetailViewState.Idle -> {
            }
            is CharacterDetailViewState.ProfileLoaded -> add(ProfileView(state.character))
            is CharacterDetailViewState.FetchDetailError -> {
            }
        }
    }
}
