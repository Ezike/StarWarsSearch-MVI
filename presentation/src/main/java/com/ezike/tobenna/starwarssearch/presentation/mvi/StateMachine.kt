package com.ezike.tobenna.starwarssearch.presentation.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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
    initialIntent: ViewIntent? = null
) {

    private val mainScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val subscriptions: CopyOnWriteArrayList<Subscription<S, ViewState>> =
        CopyOnWriteArrayList()

    @Volatile
    private var oldState: S = initialState

    private val intentsChannel: ConflatedBroadcastChannel<ViewIntent> =
        ConflatedBroadcastChannel<ViewIntent>().apply {
            if (initialIntent != null) {
                offer(initialIntent)
            }
        }

    private fun makeState() {
        intentsChannel.asFlow()
            .flatMapMerge { action ->
                intentProcessor.intentToResult(action)
            }.scan(initialState) { previous, result ->
                reducer.reduce(previous, result)
            }.distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .onEach { newState ->
                oldState = newState
                subscriptions.forEach { subscription ->
                    subscription.updateState(newState)
                }
            }.launchIn(mainScope)
    }

    init {
        makeState()
    }

    fun processIntent(intent: ViewIntent) {
        intentsChannel.offer(intent)
    }

    fun <V : ViewState> subscribe(
        subscriber: Subscriber<V>,
        transform: StateTransform<S, V>
    ) {

        val subscription: Subscription<S, V> = Subscription(subscriber, transform)
        subscriptions += subscription as Subscription<S, ViewState>
        subscription.updateState(oldState)
    }

    fun unSubscribe() {
        mainScope.cancel()
        unSubscribeComponents()
    }

    fun unSubscribeComponents() {
        subscriptions.forEach { it.dispose() }
        subscriptions.clear()
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
            oldState = newState
        }
    }

    fun dispose() {
        _subscriber = null
    }
}
