package com.ezike.tobenna.starwarssearch.testutils

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestPostExecutionThread : PostExecutionThread {

    override val main: CoroutineDispatcher
        get() = TestCoroutineDispatcher()

    override val io: CoroutineDispatcher
        get() = TestCoroutineDispatcher()

    override val default: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
}
