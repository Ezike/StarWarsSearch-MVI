package com.ezike.tobenna.starwarssearch.presentation.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

abstract class StateMachine<S : ScreenState, R : ViewResult>(
    private val intentProcessor: IntentProcessor<R>,
    private val reducer: ViewStateReducer<S, R>,
    private val initialState: S,
    initialIntent: ViewIntent? = null,
    private val config: Config = NoOpConfig
) {

    private val mainScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val viewStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)

    val viewState: StateFlow<S>
        get() = viewStateFlow.asStateFlow()

    private val intents: MutableSharedFlow<ViewIntent> =
        MutableSharedFlow<ViewIntent>(1).apply {
            if (initialIntent != null) {
                tryEmit(initialIntent)
            }
        }

    private fun makeState() {
        intents
            .mapConfig(config, intentProcessor::intentToResult)
            .scan(initialState, reducer::reduce)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .onEach(viewStateFlow::emit)
            .launchIn(mainScope)
    }

    init {
        makeState()
    }

    fun processIntent(intent: ViewIntent) {
        intents.tryEmit(intent)
    }

    fun unSubscribe() {
        mainScope.cancel()
    }
}

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
