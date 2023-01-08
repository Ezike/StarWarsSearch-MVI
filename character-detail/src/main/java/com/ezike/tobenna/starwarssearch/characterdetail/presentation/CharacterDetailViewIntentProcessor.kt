package com.ezike.tobenna.starwarssearch.characterdetail.presentation

import com.ezike.tobenna.starwarssearch.characterdetail.data.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.characterdetail.data.FilmEntity
import com.ezike.tobenna.starwarssearch.characterdetail.data.PlanetEntity
import com.ezike.tobenna.starwarssearch.characterdetail.data.SpecieEntity
import com.ezike.tobenna.starwarssearch.characterdetail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.characterdetail.presentation.CharacterDetailViewResult.CharacterDetail
import com.ezike.tobenna.starwarssearch.characterdetail.ui.LoadCharacterDetailIntent
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.error.RetryFetchCharacterDetailsIntent
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.film.RetryFetchFilmIntent
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.planet.RetryFetchPlanetIntent
import com.ezike.tobenna.starwarssearch.characterdetail.ui.views.specie.RetryFetchSpecieIntent
import com.ezike.tobenna.starwarssearch.presentation.base.InvalidViewIntentException
import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class CharacterDetailViewIntentProcessor @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository,
) : CharacterDetailIntentProcessor {

    override fun intentToResult(viewIntent: ViewIntent): Flow<CharacterDetailViewResult> =
        when (viewIntent) {
            is LoadCharacterDetailIntent -> {
                getCharacterInfo(
                    model = viewIntent.character,
                    initialResult = CharacterDetail(
                        character = viewIntent.character
                    )
                )
            }

            is RetryFetchPlanetIntent -> retryFetchPlanet(viewIntent.url)
            is RetryFetchSpecieIntent -> retryFetchSpecie(viewIntent.url)
            is RetryFetchFilmIntent -> retryFetchFilm(viewIntent.url)
            is RetryFetchCharacterDetailsIntent -> {
                getCharacterInfo(
                    model = viewIntent.character,
                    initialResult = CharacterDetailViewResult.Retrying
                )
            }
            else -> throw InvalidViewIntentException(viewIntent)
        }

    private fun getCharacterInfo(
        model: CharacterDetailModel,
        initialResult: CharacterDetailViewResult
    ): Flow<CharacterDetailViewResult> =
        characterDetailRepository.getCharacterDetail(model.url)
            .flatMapLatest { character ->
                merge(
                    getFilms(character.filmUrls),
                    getPlanet(character.planetUrl),
                    getSpecies(character.speciesUrls)
                )
            }
            .onStart { emit(initialResult) }
            .catch { error ->
                emit(CharacterDetailViewResult.FetchCharacterDetailError(model.name, error))
            }

    private fun getFilms(urls: List<String>): Flow<FilmDetailViewResult> =
        flow { emit(characterDetailRepository.fetchFilms(urls)) }
            .map<List<FilmEntity>, FilmDetailViewResult> { films ->
                FilmDetailViewResult.Success(films)
            }.onStart {
                emit(FilmDetailViewResult.Loading)
            }.catch { error ->
                error.printStackTrace()
                emit(FilmDetailViewResult.Error(error))
            }

    private suspend fun getSpecies(urls: List<String>): Flow<SpecieDetailViewResult> =
        flow { emit(characterDetailRepository.fetchSpecies(urls)) }
            .map<List<SpecieEntity>, SpecieDetailViewResult> { species ->
                SpecieDetailViewResult.Success(species)
            }.onStart {
                emit(SpecieDetailViewResult.Loading)
            }.catch { error ->
                error.printStackTrace()
                emit(SpecieDetailViewResult.Error(error))
            }

    private fun getPlanet(url: String): Flow<PlanetDetailViewResult> =
        flow { emit(characterDetailRepository.fetchPlanet(url)) }
            .map<PlanetEntity, PlanetDetailViewResult> { planet ->
                PlanetDetailViewResult.Success(planet)
            }.onStart {
                emit(PlanetDetailViewResult.Loading)
            }.catch { error ->
                error.printStackTrace()
                emit(PlanetDetailViewResult.Error(error))
            }

    private fun retryFetchPlanet(url: String): Flow<PlanetDetailViewResult> =
        characterDetailRepository.getCharacterDetail(url)
            .flatMapLatest { character -> getPlanet(character.planetUrl) }
            .catch { error -> emit(PlanetDetailViewResult.Error(error)) }

    private fun retryFetchSpecie(url: String): Flow<SpecieDetailViewResult> =
        characterDetailRepository.getCharacterDetail(url)
            .flatMapLatest { character -> getSpecies(character.speciesUrls) }
            .catch { error -> emit(SpecieDetailViewResult.Error(error)) }

    private fun retryFetchFilm(url: String): Flow<FilmDetailViewResult> =
        characterDetailRepository.getCharacterDetail(url)
            .flatMapLatest { character -> getFilms(character.filmUrls) }
            .catch { error -> emit(FilmDetailViewResult.Error(error)) }

}
