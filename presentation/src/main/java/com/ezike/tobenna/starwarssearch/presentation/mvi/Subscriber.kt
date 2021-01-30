package com.ezike.tobenna.starwarssearch.presentation.mvi

/**
 * Represents a type that wants to subscribe to state updates from the [StateMachine]
 */
interface Subscriber<State> {
    fun onNewState(state: State)
    var intentDispatcher: (ViewIntent) -> Unit
}

val NoOpIntentDispatcher: (ViewIntent) -> Unit
    get() = {}