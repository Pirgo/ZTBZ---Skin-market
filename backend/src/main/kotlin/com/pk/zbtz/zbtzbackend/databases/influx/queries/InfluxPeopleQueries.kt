package com.pk.zbtz.zbtzbackend.databases.influx.queries

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.write.Point
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.databases.influx.models.HumanInfluxModel
import org.springframework.stereotype.Service
import java.time.ZoneId

@Service
public class InfluxPeopleQueries {

    public fun InfluxAddHuman(client: InfluxDBClient, request: AddHumanRequest): HumanInfluxModel {
        val writeApi = client.writeApiBlocking
        val point = Point.measurement("people")
            .addTag("firstName", request.firstName)
            .addTag("secondName", request.secondName)
            .addField("photoUrl", request.photoUrl)
            .addField("birthday", request.birthday.atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
            .addTag("placeOfBirth", request.placeOfBirth)
            .addField("deathDay", request.deathDay?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond())
            .addField("description", request.description)

        writeApi.writePoint(point)

        return HumanInfluxModel (
            firstName = request.firstName,
            secondName = request.secondName,
            photoUrl = request.photoUrl,
            birthday = request.birthday,
            placeOfBirth = request.placeOfBirth,
            deathDay = request.deathDay,
            description = request.description,
            functions = HumanInfluxModel.FunctionsValueInflux(
                director = emptyList(),
                actor = emptyList()
            )
        )
    }
}