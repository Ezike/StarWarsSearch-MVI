package com.ezike.tobenna.starwarssearch.presentation_android

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIPresenter
import com.ezike.tobenna.starwarssearch.presentation.mvi.ScreenState
import com.ezike.tobenna.starwarssearch.presentation.mvi.StateMachine
import com.ezike.tobenna.starwarssearch.presentation.mvi.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.mvi.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewResult
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

@MainThread
abstract class ComponentManager<S : ScreenState, out R : ViewResult>(
    private val stateMachine: StateMachine<S, R>
) : ViewModel(), MVIPresenter<S> {

    override fun <VS : ViewState> subscribe(
        component: Subscriber<VS>,
        transform: StateTransform<S, VS>
    ) {
        stateMachine.subscribe(component, transform)
    }

    //TODO refactor this to be part of [Component instead]
    override fun processIntent(intent: ViewIntent) {
        stateMachine.processIntent(intent)
    }

    fun disposeAll(owner: LifecycleOwner) {
        dispose(owner) { stateMachine.unSubscribeComponents() }
    }

    override fun onCleared() {
        stateMachine.unSubscribe()
    }
}
