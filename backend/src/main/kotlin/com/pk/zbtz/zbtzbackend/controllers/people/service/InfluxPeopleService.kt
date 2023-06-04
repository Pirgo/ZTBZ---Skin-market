package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.influxdb.client.InfluxDBClientFactory
import com.influxdb.client.WriteApi
import com.influxdb.client.WriteApiBlocking
import com.influxdb.client.write.Point
import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.databases.influx.InfluxClient
import com.pk.zbtz.zbtzbackend.domain.Human
import org.springframework.stereotype.Service

@Service
class InfluxPeopleService() : PeopleService {
    override fun getAll(
        nameToSearch: String?,
        pageSize: Int?,
        offset: Int?
    ): ResponseWithStatistics<GetPeopleResponse> {
        TODO("Not yet implemented")
    }

    override fun get(humanId: String): ResponseWithStatistics<Human> {
        TODO("Not yet implemented")
    }

    override fun add(request: AddHumanRequest): ResponseWithStatistics<Human> {
        val influxClient = InfluxClient()
        val writeApi: WriteApiBlocking = influxClient.writeApiBlocking

        val point = Point.measurement("humans")
            .addTag("firstName", request.firstName)
            .addTag("secondName", request.secondName)
        request.photoUrl?.let { point.addTag("photoUrl", it) }
            ?.addField("birthday", request.birthday.toEpochDay() * 1000000)
            ?.addTag("placeOfBirth", request.placeOfBirth)
        request.deathDay?.let { point.addField("deathDay", it.toEpochDay() * 1000000) }
            ?.addTag("description", request.description)

        writeApi.writePoint(point)
        influxClient.close()
        TODO("Not yet implemented")
    }

    override fun delete(humanId: String): ResponseWithStatistics<Unit> {
        TODO("Not yet implemented")
    }
}
