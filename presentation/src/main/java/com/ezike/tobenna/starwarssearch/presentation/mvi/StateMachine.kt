package com.ezike.tobenna.starwarssearch.presentation.mvi

import com.ezike.tobenna.starwarssearch.presentation.mvi.util.Atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import java.util.concurrent.CopyOnWriteArrayList

abstract class StateMachine<S : ScreenState, R : ViewResult>(
    private val intentProcessor: IntentProcessor<R>,
    private val reducer: ViewStateReducer<S, R>,
    private val initialState: S,
    initialIntent: ViewIntent? = null,
    private val config: Config = NoOpConfig
) {

    private val mainScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val subscribers: MutableList<Subscription<S, ViewState>> =
        CopyOnWriteArrayList()

    private val cachedState: Atomic<S> = Atomic(initialState)

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
            .onEach(::notifySubscribers)
            .launchIn(mainScope)
    }

    private suspend fun notifySubscribers(newState: S) {
        cachedState.update(newState)
        subscribers.forEach { subscriber ->
            subscriber.updateState(newState)
        }
    }

    init {
        makeState()
    }

    fun processIntent(intent: ViewIntent) {
        intents.tryEmit(intent)
    }

    fun <V : ViewState> subscribe(
        subscriber: Subscriber<V>,
        transform: StateTransform<S, V>
    ) {
        val subscription: Subscription<S, V> = Subscription(subscriber, transform)
        subscribers += subscription as Subscription<S, ViewState>
        subscription.updateState(cachedState.value)
    }

    fun unSubscribe() {
        mainScope.cancel()
        unSubscribeComponents()
    }

    fun unSubscribeComponents() {
        subscribers.forEach { it.dispose() }
        subscribers.clear()
    }
}

internal class Subscription<S : ScreenState, V : ViewState>(
    subscriber: Subscriber<V>,
    private val transform: StateTransform<S, V>
) {

    private var _subscriber: Subscriber<V>? = null

    init {
        _subscriber = subscriber
    }

    private var oldState: V? = null

    fun updateState(state: S) {
        val newState: V = transform(state)
        if (oldState == null || oldState != newState) {
            _subscriber?.onNewState(newState)
            synchronized(this) {
                oldState = newState
            }
        }
    }

    fun dispose() {
        _subscriber = null
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
