package com.ezike.tobenna.starwarssearch.presentation_android

import androidx.annotation.UiThread
import com.ezike.tobenna.starwarssearch.presentation.base.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.base.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.base.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState

/**
 * Represents a basic UI component that can be part of a screen
 */
abstract class UIComponent<ComponentState : ViewState> : Subscriber<ComponentState> {

    @UiThread
    abstract fun render(state: ComponentState)

    @UiThread
    protected fun sendIntent(intent: ViewIntent) {
        dispatchIntent.invoke(intent)
    }

    override var dispatchIntent: DispatchIntent = NoOpIntentDispatcher

    override fun onNewState(state: ComponentState) {
        render(state)
    }
}

private val NoOpIntentDispatcher: DispatchIntent
    get() = {}

abstract class StatelessUIComponent : UIComponent<ViewState>() {
    override fun render(state: ViewState) {}
}
