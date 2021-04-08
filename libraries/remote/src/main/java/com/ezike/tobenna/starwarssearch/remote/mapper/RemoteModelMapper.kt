package com.ezike.tobenna.starwarssearch.remote.mapper

public interface RemoteModelMapper<in M, out E> {

    public fun mapFromModel(model: M): E

    public fun mapModelList(models: List<M>): List<E> {
        return models.mapTo(mutableListOf(), ::mapFromModel)
    }
}
