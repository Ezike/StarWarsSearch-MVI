package com.ezike.tobenna.starwarssearch.presentation.base

import kotlinx.coroutines.flow.Flow

public interface IntentProcessor<out R : ViewResult> {
    public fun intentToResult(viewIntent: ViewIntent): Flow<R>
}

public class InvalidViewIntentException(
    private val viewIntent: ViewIntent
) : IllegalArgumentException() {
    override val message: String
        get() = "Invalid intent $viewIntent"
}
