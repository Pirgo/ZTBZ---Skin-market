package com.pk.zbtz.zbtzbackend.utils.execution_timer

interface ExecutionTimer {
    fun <T> measure(block: () -> T): ElapsedTimeResult<T>

    data class ElapsedTimeResult<T>(
        val time: Long,
        val blockResult: T,
    )
}