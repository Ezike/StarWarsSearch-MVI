package com.ezike.tobenna.starwarssearch.cache.base

interface CacheModelMapper<M, E> {

    fun mapToModel(entity: E): M

    fun mapToEntity(model: M): E
}
