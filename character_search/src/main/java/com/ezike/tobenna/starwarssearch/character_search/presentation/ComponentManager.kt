package com.ezike.tobenna.starwarssearch.character_search.presentation

import androidx.lifecycle.ViewModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIPresenter
import com.ezike.tobenna.starwarssearch.presentation.mvi.ScreenState
import com.ezike.tobenna.starwarssearch.presentation.mvi.StateMachine
import com.ezike.tobenna.starwarssearch.presentation.mvi.StateTransform
import com.ezike.tobenna.starwarssearch.presentation.mvi.UIComponent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewResult
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState
import kotlinx.coroutines.flow.flowOf

abstract class ComponentManager<S : ScreenState, out R : ViewResult>(
    private val stateMachine: StateMachine<S, R>
) : ViewModel(), MVIPresenter<S> {

    override fun <VS : ViewState> subscribe(
        component: UIComponent<VS>,
        transform: StateTransform<S, VS>
    ) {
        stateMachine.subscribe(component, transform)
    }

    override fun processIntent(intent: ViewIntent) {
        stateMachine.processIntents(flowOf(intent))
    }

    override fun onCleared() {
        stateMachine.unSubscribe()
    }
}
