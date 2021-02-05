package com.ezike.tobenna.starwarssearch.cache.base

interface CacheModelMapper<M, E> {

    fun mapToModel(entity: E): M

    fun mapToEntity(model: M): E

    fun mapToEntityList(models: List<M>): List<E> {
        return models.mapTo(mutableListOf(), ::mapToEntity)
    }

    fun mapToModelList(entities: List<E>): List<M> {
        return entities.mapTo(mutableListOf(), ::mapToModel)
    }
}
