package com.ezike.tobenna.starwarssearch.testutils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * source:
 * https://github.com/ReactiveCircus/streamlined/blob/main/libraries/coroutines-test-ext
 */

public fun <T> Flow<T>.recordWith(recorder: FlowRecorder<T>) {
    onEach { recorder += it }.launchIn(recorder.coroutineScope)
}

@OptIn(ExperimentalStdlibApi::class)
public class FlowRecorder<T>(internal val coroutineScope: CoroutineScope) {

    private val values: MutableList<T> = mutableListOf()

    internal operator fun plusAssign(t: T) {
        values += t
    }

    /**
     * Takes the first [numberOfValues] recorded values emitted by the [Flow].
     */
    public fun take(numberOfValues: Int): List<T> {
        require(numberOfValues > 0) {
            "Least number of values to take is 1."
        }
        require(numberOfValues <= values.size) {
            "Taking $numberOfValues but only ${values.size} value(s) have been recorded."
        }
        val drainedValues: MutableList<T> = mutableListOf()
        while (drainedValues.size < numberOfValues) {
            drainedValues += values.removeFirst()
        }
        return drainedValues
    }

    /**
     * Takes all recorded values emitted by the [Flow].
     */
    public fun takeAll(): List<T> {
        val drainedValues: List<T> = buildList { addAll(values) }
        values.clear()
        return drainedValues
    }

    /**
     * returns the number of items emitted by the [Flow]
     */
    public val size: Int
        get() = values.size

    /**
     * Clears all recorded values emitted by the [Flow].
     */
    public fun reset() {
        values.clear()
    }
}
