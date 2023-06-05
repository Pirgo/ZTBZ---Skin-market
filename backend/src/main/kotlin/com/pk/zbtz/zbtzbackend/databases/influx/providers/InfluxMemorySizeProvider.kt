package com.pk.zbtz.zbtzbackend.databases.influx.providers

interface InfluxMemorySizeProvider {
    fun getDatabaseSizeInGigabytes(): Double
}