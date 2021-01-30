package com.ezike.tobenna.starwarssearch.presentation.mvi

import com.ezike.tobenna.starwarssearch.presentation.mvi.util.Atomic
import com.ezike.tobenna.starwarssearch.presentation.mvi.util.Config
import com.ezike.tobenna.starwarssearch.presentation.mvi.util.NoOpConfig
import com.ezike.tobenna.starwarssearch.presentation.mvi.util.mapConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan

abstract class StateMachine<S : ScreenState, R : ViewResult>(
    private val intentProcessor: IntentProcessor<R>,
    private val reducer: ViewStateReducer<S, R>,
    initialState: S,
    initialIntent: ViewIntent = NoOpIntent,
    config: Config = NoOpConfig
) {

    private val mainScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val subscribers: MutableList<Subscription<S, ViewState>> = mutableListOf()

    private val cachedState: Atomic<S> = Atomic(initialState)

    private val intents: Channel<ViewIntent> = Channel<ViewIntent>(1).apply {
        offer(initialIntent)
    }

    init {
        intents.receiveAsFlow()
            .filter { it !is NoOpIntent }
            .mapConfig(config, intentProcessor::intentToResult)
            .scan(initialState, reducer::reduce)
            .distinctUntilChanged()
            .onEach(::notifySubscribers)
            .launchIn(mainScope)
    }

    private suspend fun notifySubscribers(newState: S) {
        cachedState.update(newState)
        subscribers.forEach { subscriber ->
            subscriber.updateState(newState)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <V : ViewState> subscribe(
        subscriber: Subscriber<V>,
        transform: StateTransform<S, V>
    ) {
        val subscription: Subscription<S, V> = Subscription(subscriber, transform)
        subscribers += subscription as Subscription<S, ViewState>
        subscription.updateState(cachedState.value)
        subscription.registerIntents(intents::offer)
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


