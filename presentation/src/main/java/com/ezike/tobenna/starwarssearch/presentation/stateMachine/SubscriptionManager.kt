package com.ezike.tobenna.starwarssearch.presentation.stateMachine

import com.ezike.tobenna.starwarssearch.presentation.base.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.base.ScreenState
import com.ezike.tobenna.starwarssearch.presentation.base.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.base.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

internal class SubscriptionManager(
    private val scope: CoroutineScope,
    initialState: ScreenState
) {

    private val subscriberActor = scope.actor<SubscriberAction> {

        val subscribers: MutableList<Subscription<ScreenState, ViewState>> = mutableListOf()

        var cachedState: ScreenState = initialState

        for (action in channel) {
            when (action) {
                is SubscriberAction.Subscribe -> {
                    val subscription: Subscription<ScreenState, ViewState> =
                        Subscription(action.subscriber, action.transform)
                    subscribers += subscription
                    subscription.updateState(cachedState)
                    subscription.onIntentDispatch(action.onIntent)
                }
                is SubscriberAction.UpdateState -> {
                    cachedState = action.state
                    subscribers.forEach { subscriber ->
                        subscriber.updateState(action.state)
                    }
                }
                SubscriberAction.Dispose -> {
                    subscribers.forEach { it.dispose() }
                    subscribers.clear()
                }
            }
        }
    }

    fun subscribe(
        subscriber: Subscriber<ViewState>,
        transform: StateTransform<ScreenState, ViewState>,
        dispatchIntent: DispatchIntent
    ) {
        scope.launch {
            subscriberActor.send(
                SubscriberAction.Subscribe(
                    subscriber,
                    transform,
                    dispatchIntent
                )
            )
        }
    }

    fun unSubscribe() {
        scope.launch {
            subscriberActor.send(SubscriberAction.Dispose)
        }
    }

    fun updateState(state: ScreenState) {
        scope.launch {
            subscriberActor.send(SubscriberAction.UpdateState(state))
        }
    }

    private sealed interface SubscriberAction {
        data class Subscribe(
            val subscriber: Subscriber<ViewState>,
            val transform: StateTransform<ScreenState, ViewState>,
            val onIntent: DispatchIntent
        ) : SubscriberAction

        data class UpdateState(val state: ScreenState) : SubscriberAction
        object Dispose : SubscriberAction
    }
}
