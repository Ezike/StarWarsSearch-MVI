package com.ezike.tobenna.starwarssearch.presentation.mvi.stateMachine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge

public sealed class Config
public object Latest : Config()
internal object NoOpConfig : Config()

internal inline fun <S, T> Flow<T>.mapConfig(
    config: Config,
    crossinline transform: suspend (T) -> Flow<S>
): Flow<S> = when (config) {
    Latest -> flatMapLatest(transform = transform)
    NoOpConfig -> flatMapMerge { transform(it) }
}
