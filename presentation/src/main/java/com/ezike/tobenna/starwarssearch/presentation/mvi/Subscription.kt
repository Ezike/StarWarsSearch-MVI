package com.ezike.tobenna.starwarssearch.presentation.mvi

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
            synchronized(this) {
                oldState = newState
            }
        }
    }

    fun registerIntents(action: (ViewIntent) -> Unit) {
        _subscriber?.intentDispatcher = action
    }

    fun dispose() {
        _subscriber?.intentDispatcher = NoOpIntentDispatcher
        _subscriber = null
    }
}
