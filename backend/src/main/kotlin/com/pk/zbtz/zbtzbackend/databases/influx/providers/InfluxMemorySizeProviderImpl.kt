package com.pk.zbtz.zbtzbackend.databases.influx.providers

import com.influxdb.client.InfluxDBClientFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class InfluxMemorySizeProviderImpl : InfluxMemorySizeProvider {
    override fun getDatabaseSizeInGigabytes(): Double {
        // TODO("get database size")
//        val buckets = influxDBClient.getBucketsApi().findBuckets(BucketsOptions().orgID(influxDBClient)).buckets
//
//        val targetBucket = buckets.find { it.name == bucket }
//        val dataSizeBytes = targetBucket?.sizeBytes ?: -1
//
//        return dataSizeBytes / (1024.0 * 1024.0 * 1024.0) // Convert bytes to gigabytes
        return 1.37
    }
}