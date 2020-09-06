package com.ezike.tobenna.starwarssearch.presentation.mvi

/**
 * Represents a type that wants to subscribe to state updates from the [StateMachine]
 */
interface Subscriber<State> {
    fun onNewState(state: State)
}

/**
 * Represents a basic UI component that can be part of a screen
 */
abstract class UIComponent<ComponentState : ViewState> : Subscriber<ComponentState> {

    abstract fun render(state: ComponentState)
    override fun onNewState(state: ComponentState) {
        render(state)
    }
}
