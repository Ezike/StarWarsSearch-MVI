package com.ezike.tobenna.starwarssearch.presentation.mvi

import kotlinx.coroutines.flow.Flow

interface IntentProcessor<out R : ViewResult> {
    fun intentToResult(viewIntent: ViewIntent): Flow<R>
}
