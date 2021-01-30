package com.ezike.tobenna.starwarssearch.presentation_android

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.MVIPresenter
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.NoOpTransform
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ScreenState
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewResult
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.stateMachine.StateMachine

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

    override fun <VS : ViewState> subscribe(component: Subscriber<VS>) {
        stateMachine.subscribe(component, NoOpTransform())
    }

    fun disposeAll(owner: LifecycleOwner) {
        dispose(owner) { stateMachine.unSubscribeComponents() }
    }

    override fun onCleared() {
        stateMachine.unSubscribe()
    }
}
