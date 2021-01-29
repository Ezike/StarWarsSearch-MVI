package com.ezike.tobenna.starwarssearch.presentation.mvi.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge

sealed class Config
object Latest : Config()
object NoOpConfig : Config()

internal inline fun <S, T> Flow<T>.mapConfig(
    config: Config,
    crossinline transform: suspend (T) -> Flow<S>
): Flow<S> = when (config) {
    Latest -> flatMapLatest(transform = transform)
    NoOpConfig -> flatMapMerge { transform(it) }
}
