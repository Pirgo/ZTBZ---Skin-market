package com.pk.zbtz.zbtzbackend.utils.execution_timer

import org.springframework.stereotype.Component

@Component
class ExecutionTimerImpl : ExecutionTimer {
    override fun <T> measure(block: () -> T): ExecutionTimer.ElapsedTimeResult<T> {
        val startTime = System.currentTimeMillis()
        val blockResult = block()
        val endTime = System.currentTimeMillis()

        return ExecutionTimer.ElapsedTimeResult(
            time = endTime - startTime,
            blockResult = blockResult,
        )
    }
}