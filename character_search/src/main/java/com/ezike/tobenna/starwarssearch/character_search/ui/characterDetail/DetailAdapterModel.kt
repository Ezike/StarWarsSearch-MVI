package com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail

import androidx.annotation.StringRes
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.model.FilmModel
import com.ezike.tobenna.starwarssearch.character_search.model.PlanetModel
import com.ezike.tobenna.starwarssearch.character_search.model.SpecieModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

sealed class DetailAdapterModel : ViewState {
    object LoadingView : DetailAdapterModel()
    data class EmptyView(@StringRes val name: Int) : DetailAdapterModel()
    data class ErrorView(val cause: String, @StringRes val name: Int) : DetailAdapterModel()
    data class ProfileView(val character: CharacterModel) : DetailAdapterModel()
    data class PlanetView(val planetModel: PlanetModel) : DetailAdapterModel()
    data class SpecieView(val specie: SpecieModel) : DetailAdapterModel()
    data class FilmView(val filmModel: FilmModel) : DetailAdapterModel()
    data class Header(@StringRes val title: Int) : DetailAdapterModel()
}
