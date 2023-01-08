package com.ezike.tobenna.starwarssearch.characterdetail.mapper

import com.ezike.tobenna.starwarssearch.characterdetail.data.SpecieEntity
import com.ezike.tobenna.starwarssearch.characterdetail.model.SpecieModel
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

internal class SpecieModelMapper @Inject constructor() : ModelMapper<SpecieModel, SpecieEntity> {

    override fun mapToModel(domain: SpecieEntity): SpecieModel =
        SpecieModel(domain.name, domain.language, domain.homeworld ?: "")

    override fun mapToDomain(model: SpecieModel): SpecieEntity =
        SpecieEntity(model.name, model.language, model.homeWorld)
}
