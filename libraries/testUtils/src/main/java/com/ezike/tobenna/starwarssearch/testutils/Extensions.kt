package com.ezike.tobenna.starwarssearch.testutils

import com.google.common.truth.IterableSubject
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert
import org.junit.function.ThrowingRunnable

inline fun <reified T : Throwable> TestCoroutineScope.assertThrows(
    crossinline runnable: suspend () -> Unit
): T {
    val throwingRunnable = ThrowingRunnable {
        val job: Deferred<Unit> = async { runnable() }
        job.getCompletionExceptionOrNull()?.run { throw this }
        job.cancel()
    }
    return Assert.assertThrows(T::class.java, throwingRunnable)
}

inline fun <reified T> IterableSubject.containsElements(vararg instance: T) {
    containsExactlyElementsIn(instance).inOrder()
}
