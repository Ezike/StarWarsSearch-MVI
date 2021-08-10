package com.ezike.tobenna.starwarssearch.presentation.stateMachine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge

/**
 * This sets whether the object consuming state from the [StateMachine] will get
 * intermediate state updates or just the latest state (intermediate values are dropped)
 **/

public sealed interface RenderStrategy {
    public object Latest : RenderStrategy
    public object Intermediate : RenderStrategy
}

internal inline fun <S, T> Flow<T>.renderWith(
    config: RenderStrategy,
    crossinline transform: suspend (T) -> Flow<S>
): Flow<S> = when (config) {
    RenderStrategy.Latest -> flatMapLatest(transform = transform)
    RenderStrategy.Intermediate -> flatMapMerge { transform(it) }
}
