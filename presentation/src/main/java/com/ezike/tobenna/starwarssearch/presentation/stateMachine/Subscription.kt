package com.ezike.tobenna.starwarssearch.presentation.stateMachine

import com.ezike.tobenna.starwarssearch.presentation.base.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.base.ScreenState
import com.ezike.tobenna.starwarssearch.presentation.base.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.base.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

internal class Subscription<S : ScreenState, V : ViewState>(
    private var subscriber: Subscriber<V>?,
    private val transform: StateTransform<S, V>
) {

    private var oldState: V? = null

    fun updateState(state: S) {
        val newState: V = transform(state)
        if (oldState == null || oldState != newState) {
            oldState = newState
            subscriber?.onNewState(newState)
        }
    }

    fun onIntentDispatch(dispatchIntent: DispatchIntent) {
        subscriber?.dispatchIntent = dispatchIntent
    }

    fun dispose() {
        subscriber = null
    }
}
