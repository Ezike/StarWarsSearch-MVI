package com.ezike.tobenna.starwarssearch.characterdetail.mapper

import com.ezike.tobenna.starwarssearch.characterdetail.model.SpecieModel
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Specie
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

class SpecieModelMapper @Inject constructor() : ModelMapper<SpecieModel, Specie> {

    override fun mapToModel(domain: Specie): SpecieModel {
        return SpecieModel(domain.name, domain.language, domain.homeWorld)
    }

    override fun mapToDomain(model: SpecieModel): Specie {
        return Specie(model.name, model.language, model.homeWorld)
    }
}
