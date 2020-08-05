package com.ezike.tobenna.starwarssearch.data.mapper

import com.ezike.tobenna.starwarssearch.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.domain.model.CharacterDetail
import javax.inject.Inject

class CharacterDetailEntityMapper @Inject constructor() : EntityMapper<CharacterDetailEntity, CharacterDetail> {

    override fun mapFromEntity(entity: CharacterDetailEntity): CharacterDetail {
        return CharacterDetail(
            entity.filmUrls,
            entity.planetUrl,
            entity.speciesUrls,
            entity.url
        )
    }

    override fun mapToEntity(domain: CharacterDetail): CharacterDetailEntity {
        return CharacterDetailEntity(
            domain.filmUrls,
            domain.planetUrl,
            domain.speciesUrls,
            domain.url
        )
    }
}
