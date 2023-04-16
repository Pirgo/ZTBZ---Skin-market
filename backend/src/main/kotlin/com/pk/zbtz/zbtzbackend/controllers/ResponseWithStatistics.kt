package com.pk.zbtz.zbtzbackend.controllers

import com.pk.zbtz.zbtzbackend.domain.Statistics

data class ResponseWithStatistics<T>(
    val data: T? = null,
    val statistics: Statistics,
)