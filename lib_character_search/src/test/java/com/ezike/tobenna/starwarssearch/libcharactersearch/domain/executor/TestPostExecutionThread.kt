package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

internal class TestPostExecutionThread : PostExecutionThread {

    override val main: CoroutineDispatcher
        get() = TestCoroutineDispatcher()

    override val io: CoroutineDispatcher
        get() = TestCoroutineDispatcher()

    override val default: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
}
