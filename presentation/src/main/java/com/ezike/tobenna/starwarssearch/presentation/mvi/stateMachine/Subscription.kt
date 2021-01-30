package com.ezike.tobenna.starwarssearch.presentation.mvi.stateMachine

import com.ezike.tobenna.starwarssearch.presentation.mvi.base.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.NoOpIntentDispatcher
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ScreenState
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState

internal class Subscription<S : ScreenState, V : ViewState>(
    subscriber: Subscriber<V>,
    private val transform: StateTransform<S, V>
) {

    private var _subscriber: Subscriber<V>? = subscriber


    private var oldState: V? = null

    fun updateState(state: S) {
        val newState: V = transform(state)
        if (oldState == null || oldState != newState) {
            _subscriber?.onNewState(newState)
            oldState = newState
        }
    }

    fun registerIntents(dispatchIntent: DispatchIntent) {
        _subscriber?.dispatchIntent = dispatchIntent
    }

    fun dispose() {
        _subscriber?.dispatchIntent = NoOpIntentDispatcher
        _subscriber = null
    }
}
