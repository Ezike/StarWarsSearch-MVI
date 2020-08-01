package com.ezike.tobenna.starwarssearch.domain.fakes

import com.ezike.tobenna.starwarssearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.fakes.FakeCharacterRepository.Companion.ERROR_MSG
import com.ezike.tobenna.starwarssearch.domain.usecase.FlowUseCase
import java.net.SocketTimeoutException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert
import org.junit.function.ThrowingRunnable

class ExceptionUseCase(private val postExecutionThread: PostExecutionThread) :
    FlowUseCase<Unit, Unit>() {

    override fun execute(params: Unit?): Flow<Unit> {
        return flow {
            throw SocketTimeoutException(ERROR_MSG)
        }
    }

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io
}

class ParamUseCase(private val postExecutionThread: PostExecutionThread) :
    FlowUseCase<String, String>() {

    override fun execute(params: String?): Flow<String> {
        requireParams(params)
        return flowOf(params)
    }

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io
}

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
