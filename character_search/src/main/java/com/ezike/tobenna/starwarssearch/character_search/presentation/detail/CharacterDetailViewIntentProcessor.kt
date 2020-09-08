package com.ezike.tobenna.starwarssearch.character_search.presentation.detail

import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.LoadCharacterDetailIntent
import com.ezike.tobenna.starwarssearch.character_search.views.detail.RetryFetchCharacterDetailsIntent
import com.ezike.tobenna.starwarssearch.character_search.views.detail.RetryFetchFilmIntent
import com.ezike.tobenna.starwarssearch.character_search.views.detail.RetryFetchPlanetIntent
import com.ezike.tobenna.starwarssearch.character_search.views.detail.RetryFetchSpecieIntent
import com.ezike.tobenna.starwarssearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.domain.model.Specie
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchFilms
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchPlanet
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchSpecies
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.GetCharacterDetail
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CharacterDetailViewIntentProcessor @Inject constructor(
    private val fetchPlanet: FetchPlanet,
    private val fetchSpecies: FetchSpecies,
    private val fetchFilms: FetchFilms,
    private val getCharacterDetail: GetCharacterDetail,
    private val characterModelMapper: CharacterModelMapper
) : CharacterDetailIntentProcessor {

    override fun intentToResult(viewIntent: ViewIntent): Flow<CharacterDetailViewResult> {
        return when (viewIntent) {
            is LoadCharacterDetailIntent -> {
                getCharacterInfo(
                    viewIntent.character,
                    CharacterDetailViewResult.CharacterDetail(
                        characterModelMapper.mapToDomain(viewIntent.character)
                    )
                )
            }
            is RetryFetchPlanetIntent -> retryFetchPlanet(viewIntent.url)
            is RetryFetchSpecieIntent -> retryFetchSpecie(viewIntent.url)
            is RetryFetchFilmIntent -> retryFetchFilm(viewIntent.url)
            is RetryFetchCharacterDetailsIntent ->
                getCharacterInfo(viewIntent.character, CharacterDetailViewResult.Retrying)
            else -> throw IllegalArgumentException("Invalid intent $viewIntent")
        }
    }

    private fun getCharacterInfo(
        model: CharacterModel,
        start: CharacterDetailViewResult
    ): Flow<CharacterDetailViewResult> {
        return getCharacterDetail(model.url)
            .flatMapLatest { character ->
                merge(
                    getFilms(character.filmUrls),
                    getPlanet(character.planetUrl),
                    getSpecies(character.speciesUrls)
                )
            }
            .onStart { emit(start) }
            .catch { error ->
                emit(
                    CharacterDetailViewResult.FetchCharacterDetailError(model.name, error)
                )
            }
    }

    private fun getFilms(urls: List<String>): Flow<FilmDetailViewResult> {
        return fetchFilms(urls)
            .map<List<Film>, FilmDetailViewResult> { films ->
                FilmDetailViewResult.Success(films)
            }.onStart {
                emit(FilmDetailViewResult.Loading)
            }.catch { error ->
                error.printStackTrace()
                emit(FilmDetailViewResult.Error(error))
            }
    }

    private fun getSpecies(urls: List<String>): Flow<SpecieDetailViewResult> {
        return fetchSpecies(urls)
            .map<List<Specie>, SpecieDetailViewResult> { species ->
                SpecieDetailViewResult.Success(species)
            }.onStart {
                emit(SpecieDetailViewResult.Loading)
            }.catch { error ->
                error.printStackTrace()
                emit(SpecieDetailViewResult.Error(error))
            }
    }

    private fun getPlanet(url: String): Flow<PlanetDetailViewResult> {
        return fetchPlanet(url)
            .map<Planet, PlanetDetailViewResult> { planet ->
                PlanetDetailViewResult.Success(planet)
            }.onStart {
                emit(PlanetDetailViewResult.Loading)
            }.catch { error ->
                error.printStackTrace()
                emit(PlanetDetailViewResult.Error(error))
            }
    }

    private fun retryFetchPlanet(url: String): Flow<PlanetDetailViewResult> {
        return getCharacterDetail(url)
            .flatMapLatest { character -> getPlanet(character.planetUrl) }
            .catch { error -> emit(PlanetDetailViewResult.Error(error)) }
    }

    private fun retryFetchSpecie(url: String): Flow<SpecieDetailViewResult> {
        return getCharacterDetail(url)
            .flatMapLatest { character -> getSpecies(character.speciesUrls) }
            .catch { error -> emit(SpecieDetailViewResult.Error(error)) }
    }

    private fun retryFetchFilm(url: String): Flow<FilmDetailViewResult> {
        return getCharacterDetail(url)
            .flatMapLatest { character -> getFilms(character.filmUrls) }
            .catch { error -> emit(FilmDetailViewResult.Error(error)) }
    }
}
