package com.ezike.tobenna.starwarssearch.core.executor

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.executor.PostExecutionThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PostExecutionThreadImpl @Inject constructor() : PostExecutionThread {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
}
