package com.ezike.tobenna.starwarssearch.presentation.base

/**
 * Represents an object that wants to subscribe to state updates from the [StateMachine]
 */
public interface Subscriber<State> {
    public fun onNewState(state: State)
    public var dispatchIntent: DispatchIntent
}
