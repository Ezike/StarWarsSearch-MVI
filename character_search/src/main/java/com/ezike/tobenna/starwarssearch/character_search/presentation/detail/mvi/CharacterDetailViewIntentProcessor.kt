package com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi

import com.ezike.tobenna.starwarssearch.character_search.mapper.CharacterModelMapper
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterDetailIntentProcessor
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent.LoadCharacterDetail
import com.ezike.tobenna.starwarssearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.domain.model.Planet
import com.ezike.tobenna.starwarssearch.domain.model.Specie
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchCharacterDetail
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchFilms
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchPlanet
import com.ezike.tobenna.starwarssearch.domain.usecase.detail.FetchSpecies
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart

class CharacterDetailViewIntentProcessor @Inject constructor(
    private val fetchCharacterDetail: FetchCharacterDetail,
    private val fetchPlanet: FetchPlanet,
    private val fetchSpecies: FetchSpecies,
    private val fetchFilms: FetchFilms,
    private val characterModelMapper: CharacterModelMapper
) : CharacterDetailIntentProcessor {

    override fun intentToResult(viewIntent: CharacterDetailViewIntent): Flow<CharacterDetailViewResult> {
        return when (viewIntent) {
            CharacterDetailViewIntent.Idle -> flowOf(CharacterDetailViewResult.Idle)
            is LoadCharacterDetail -> fetchCharacterDetail(viewIntent.character.url)
                .flatMapLatest { character ->
                    merge(
                        getFilms(character.filmUrls),
                        getPlanet(character.planetUrl),
                        getSpecies(character.speciesUrls)
                    )
                }
                .onStart {
                    emit(
                        CharacterDetailViewResult.CharacterDetail(
                            characterModelMapper.mapToDomain(viewIntent.character)
                        )
                    )
                }
                .catch { error -> emit(CharacterDetailViewResult.FetchCharacterDetailError(error)) }
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
}
