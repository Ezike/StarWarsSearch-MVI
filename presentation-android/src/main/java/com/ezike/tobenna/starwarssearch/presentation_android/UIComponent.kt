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

@Suppress("FunctionName")
inline fun <reified V : ViewState> UIRenderer(crossinline renderer: (V) -> Unit): UIComponent<V> =
    object : UIComponent<V>() {
        override fun render(state: V) {
            renderer(state)
        }
    }
