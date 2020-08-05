package com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent

sealed class CharacterDetailViewIntent : ViewIntent {
    object Idle : CharacterDetailViewIntent()
    data class LoadCharacterDetail(val character: CharacterModel) : CharacterDetailViewIntent()
    data class RetryFetchPlanet(val url: String) : CharacterDetailViewIntent()
    data class RetryFetchSpecie(val url: String) : CharacterDetailViewIntent()
    data class RetryFetchFilm(val url: String) : CharacterDetailViewIntent()
    data class RetryFetchCharacterDetails(val character: CharacterModel) : CharacterDetailViewIntent()
}
