package com.ezike.tobenna.starwarssearch.presentation.stateMachine

import com.ezike.tobenna.starwarssearch.presentation.base.IntentProcessor
import com.ezike.tobenna.starwarssearch.presentation.base.NoOpIntent
import com.ezike.tobenna.starwarssearch.presentation.base.ScreenState
import com.ezike.tobenna.starwarssearch.presentation.base.StateReducer
import com.ezike.tobenna.starwarssearch.presentation.base.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.base.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.base.ViewResult
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan

public abstract class StateMachine<S : ScreenState, R : ViewResult>(
    private val intentProcessor: IntentProcessor<R>,
    private val reducer: StateReducer<S, R>,
    initialState: S,
    initialIntent: ViewIntent = NoOpIntent,
    strategy: RenderStrategy = RenderStrategy.Intermediate
) {

    private val mainScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    private val intents = Channel<ViewIntent>(capacity = 1).apply {
        trySend(initialIntent)
    }

    private val subscriptionManager = SubscriptionManager(mainScope, initialState)

    init {
        intents.receiveAsFlow()
            .filter { it !is NoOpIntent }
            .renderWith(strategy, intentProcessor::intentToResult)
            .scan(initialState, reducer::reduce)
            .distinctUntilChanged()
            .onEach(subscriptionManager::updateState)
            .launchIn(
                CoroutineScope(
                    mainScope.coroutineContext[Job]!! + Dispatchers.IO
                )
            )
    }

    @Suppress("UNCHECKED_CAST")
    public fun <V : ViewState> subscribe(
        subscriber: Subscriber<V>,
        transform: StateTransform<S, V>
    ) {
        subscriptionManager.subscribe(
            subscriber = subscriber as Subscriber<ViewState>,
            transform = transform as StateTransform<ScreenState, ViewState>,
            dispatchIntent = intents::trySend
        )
    }

    public fun unSubscribe() {
        unSubscribeComponents()
        mainScope.cancel()
    }

    public fun unSubscribeComponents() {
        subscriptionManager.unSubscribe()
    }
}
