package com.ezike.tobenna.starwarssearch.presentation.mvi.base

/**
 * Represents a type that wants to subscribe to state updates from the [StateMachine]
 */
public interface Subscriber<State> {
    public fun onNewState(state: State)
    public var dispatchIntent: DispatchIntent
}

internal val NoOpIntentDispatcher: DispatchIntent
    get() = {}
