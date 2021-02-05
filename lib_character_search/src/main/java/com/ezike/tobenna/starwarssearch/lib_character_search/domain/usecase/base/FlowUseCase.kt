package com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * A use case in Clean Architecture represents an execution unit of asynchronous work.
 * A [FlowUseCase] exposes a cold stream of values implemented with Kotlin [Flow].
 *
 * Work will be executed on thread as specified by the [dispatcher] of the use case.
 */
abstract class FlowUseCase<in Params, out T>() {

    /**
     * The coroutine context this use case should execute on.
     */
    abstract val dispatcher: CoroutineDispatcher

    /**
     * Function which builds Flow instance based on given arguments
     * @param params use case arguments
     */
    abstract fun execute(params: Params? = null): Flow<T>

    operator fun invoke(params: Params? = null): Flow<T> = execute(params).flowOn(dispatcher)
}
