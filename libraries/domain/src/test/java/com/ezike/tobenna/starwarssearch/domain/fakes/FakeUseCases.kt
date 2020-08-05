package com.ezike.tobenna.starwarssearch.domain.fakes

import com.ezike.tobenna.starwarssearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.fakes.FakeSearchRepository.Companion.ERROR_MSG
import com.ezike.tobenna.starwarssearch.domain.usecase.base.FlowUseCase
import java.net.SocketTimeoutException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

internal class ExceptionUseCase(private val postExecutionThread: PostExecutionThread) :
    FlowUseCase<Unit, Unit>() {

    override fun execute(params: Unit?): Flow<Unit> {
        return flow {
            throw SocketTimeoutException(ERROR_MSG)
        }
    }

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io
}

internal class ParamUseCase(private val postExecutionThread: PostExecutionThread) :
    FlowUseCase<String, String>() {

    override fun execute(params: String?): Flow<String> {
        requireParams(params)
        return flowOf(params)
    }

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io
}
