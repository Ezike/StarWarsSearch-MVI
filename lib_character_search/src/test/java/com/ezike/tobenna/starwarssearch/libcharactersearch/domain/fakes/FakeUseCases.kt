package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.fakes

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.fakes.FakeSearchRepository.Companion.ERROR_MSG
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.net.SocketTimeoutException

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
