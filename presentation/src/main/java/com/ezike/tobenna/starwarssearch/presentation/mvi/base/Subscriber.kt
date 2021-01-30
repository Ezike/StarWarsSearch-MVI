package com.ezike.tobenna.starwarssearch.presentation.mvi.base

/**
 * Represents a type that wants to subscribe to state updates from the [StateMachine]
 */
interface Subscriber<State> {
    fun onNewState(state: State)
    var dispatchIntent: DispatchIntent
}

val NoOpIntentDispatcher: DispatchIntent
    get() = {}