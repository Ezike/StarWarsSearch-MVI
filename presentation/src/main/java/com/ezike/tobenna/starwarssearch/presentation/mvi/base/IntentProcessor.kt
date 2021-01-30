package com.ezike.tobenna.starwarssearch.presentation.mvi.base

import kotlinx.coroutines.flow.Flow

interface IntentProcessor<out R : ViewResult> {
    fun intentToResult(viewIntent: ViewIntent): Flow<R>
}

class InvalidViewIntentException(private val viewIntent: ViewIntent) : IllegalArgumentException() {
    override val message: String?
        get() = "Invalid intent $viewIntent"
}