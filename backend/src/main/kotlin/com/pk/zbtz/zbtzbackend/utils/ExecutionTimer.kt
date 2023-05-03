package com.pk.zbtz.zbtzbackend.utils

interface ExecutionTimer {
    fun <T> measure(block: () -> T): ElapsedTimeResult<T>

    data class ElapsedTimeResult<T>(
        val time: Long,
        val blockResult: T,
    )
}