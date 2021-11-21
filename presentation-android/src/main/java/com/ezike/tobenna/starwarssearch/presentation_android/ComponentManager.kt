package com.ezike.tobenna.starwarssearch.presentation_android

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.ezike.tobenna.starwarssearch.presentation.base.BaseComponentManager
import com.ezike.tobenna.starwarssearch.presentation.base.NoOpTransform
import com.ezike.tobenna.starwarssearch.presentation.base.ScreenState
import com.ezike.tobenna.starwarssearch.presentation.base.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.base.Subscriber
import com.ezike.tobenna.starwarssearch.presentation.base.ViewResult
import com.ezike.tobenna.starwarssearch.presentation.base.ViewState
import com.ezike.tobenna.starwarssearch.presentation.stateMachine.StateMachine

@MainThread
abstract class ComponentManager<S : ScreenState, out R : ViewResult>(
    private val stateMachine: StateMachine<S, R>
) : ViewModel(), BaseComponentManager<S> {

    override fun <VS : ViewState> subscribe(
        component: Subscriber<VS>,
        stateTransform: StateTransform<S, VS>
    ) {
        stateMachine.subscribe(component, stateTransform)
    }

    override fun <VS : ViewState> subscribe(component: Subscriber<VS>) {
        stateMachine.subscribe(component, NoOpTransform())
    }

    fun disposeAll(owner: LifecycleOwner) {
        dispose(
            lifecycleOwner = owner,
            action = stateMachine::unSubscribeComponents
        )
    }

    override fun onCleared() {
        stateMachine.unSubscribe()
    }
}
