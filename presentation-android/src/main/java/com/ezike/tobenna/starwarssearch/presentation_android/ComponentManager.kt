package com.ezike.tobenna.starwarssearch.presentation_android

import androidx.lifecycle.ViewModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIPresenter
import com.ezike.tobenna.starwarssearch.presentation.mvi.ScreenState
import com.ezike.tobenna.starwarssearch.presentation.mvi.StateMachine
import com.ezike.tobenna.starwarssearch.presentation.mvi.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.mvi.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewResult
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState
import kotlinx.coroutines.flow.StateFlow

abstract class ComponentManager<S : ScreenState, out R : ViewResult>(
    private val stateMachine: StateMachine<S, R>
) : ViewModel(), MVIPresenter<S> {

    override val viewState: StateFlow<S>
        get() = stateMachine.viewState

    override fun processIntent(intent: ViewIntent) {
        stateMachine.processIntent(intent)
    }

    override fun onCleared() {
        stateMachine.unSubscribe()
    }
}
