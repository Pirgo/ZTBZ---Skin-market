package com.pk.zbtz.zbtzbackend.databases.influx

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.InfluxDBClientFactory

class InfluxClient(
    private val influxUrl: String = "http://localhost:8086",
    private val influxToken: String = "ztbzToken",
    private val organization: String = "ztbzDatabase",
    private val bucket: String = "ztbzTable",
) {
    fun create(): InfluxDBClient {
        return InfluxDBClientFactory.create(influxUrl, influxToken.toCharArray(), organization, bucket)
    }
}