package com.ezike.tobenna.starwarssearch.presentation_android

import androidx.annotation.UiThread
import com.ezike.tobenna.starwarssearch.presentation.mvi.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

/**
 * Represents a basic UI component that can be part of a screen
 */
abstract class UIComponent<ComponentState : ViewState> : Subscriber<ComponentState> {

    @UiThread
    abstract fun render(state: ComponentState)

    override fun onNewState(state: ComponentState) {
        render(state)
    }
}
