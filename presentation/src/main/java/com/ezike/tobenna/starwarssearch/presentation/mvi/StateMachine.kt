package com.ezike.tobenna.starwarssearch.presentation.mvi

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import java.util.concurrent.CopyOnWriteArrayList

abstract class StateMachine<I : ViewIntent, S : ScreenState, out R : ViewResult>(
    private val intentProcessor: IntentProcessor<I, R>,
    private val reducer: ViewStateReducer<S, R>,
    initialState: S,
    initialIntent: I? = null
) {

    private val subscriptions: CopyOnWriteArrayList<Subscription<S, ViewState>> =
        CopyOnWriteArrayList()

    private var oldState: S = initialState

    private val intentsChannel: ConflatedBroadcastChannel<I> =
        ConflatedBroadcastChannel<I>().apply {
            if (initialIntent != null) {
                offer(initialIntent)
            }
        }

    fun <V : ViewState> subscribe(
        subscriber: Subscriber<V>,
        transform: StateTransform<S, V>
    ) {
        subscriptions.forEach { subscription ->
            if (subscription.subscriber == subscriber) {
                subscriptions.remove(subscriber)
            }
        }
        val subscription: Subscription<S, V> = Subscription(subscriber, transform)
        subscriptions += subscription as Subscription<S, ViewState>
        subscription.updateState(oldState)
    }

    fun unSubscribe() {
        subscriptions.clear()
    }

    fun processIntents(intents: Flow<I>): Flow<I> = intents.onEach { viewIntents ->
        intentsChannel.offer(viewIntents)
    }

    val processor: Flow<S> = intentsChannel.asFlow()
        .flatMapMerge { action ->
            intentProcessor.intentToResult(action)
        }.scan(initialState) { previous, result ->
            reducer.reduce(previous, result)
        }.distinctUntilChanged()
        .onEach { newState ->
            oldState = newState
            subscriptions.forEach { subscription ->
                subscription.updateState(newState)
            }
        }
}

internal class Subscription<S : ScreenState, V : ViewState>(
    val subscriber: Subscriber<V>,
    private val transform: StateTransform<S, V>
) {

    private var oldState: V? = null

    fun updateState(state: S) {
        val newState: V = transform(state)
        if (oldState == null || oldState != newState) {
            subscriber.onNewState(newState)
            oldState = newState
        }
    }
}
