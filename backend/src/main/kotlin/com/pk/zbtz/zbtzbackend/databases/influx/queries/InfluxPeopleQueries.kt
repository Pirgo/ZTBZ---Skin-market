package com.pk.zbtz.zbtzbackend.databases.influx.queries


import com.influxdb.client.InfluxDBClient
import com.influxdb.client.write.Point
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.databases.influx.models.HumanInfluxModel
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Service
public class InfluxPeopleQueries {

    fun influxAddHuman(client: InfluxDBClient, request: AddHumanRequest): HumanInfluxModel {
        val writeApi = client.writeApiBlocking
        val id = UUID.randomUUID().toString()
        val point = Point.measurement("people")
            .addTag("id", id)
            .addTag("firstName", request.firstName)
            .addTag("secondName", request.secondName)
            .addTag("photoUrl", request.photoUrl)
            .addField("birthday", request.birthday.atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
            .addTag("placeOfBirth", request.placeOfBirth)
            .addField("deathDay", request.deathDay?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond())
            .addTag("description", request.description)

        writeApi.writePoint(point)

        return HumanInfluxModel (
            id = id,
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

    fun influxGetHuman(client: InfluxDBClient, humanId: String): HumanInfluxModel? {

        val query = """from(bucket: "ztbzTable")
            |> range(start: 0)
            |> filter(fn: (r) => r["_measurement"] == "people" and r["id"] == "$humanId")
            |> last()"""

        val queryApi = client.queryApi
        val queryResult = queryApi.query(query.trimIndent(), "ztbzDatabase")

        var id: String? = null
        var firstName: String = ""
        var secondName: String = ""
        var photoUrl: String = ""
        var birthday: Long = 0
        var placeOfBirth: String = ""
        var deathDay: Long? = null
        var description: String = ""

        for (rec in queryResult) {
            for (dat in rec.records) {

                id = dat.getValueByKey("id").toString()
                firstName = dat.getValueByKey("firstName").toString()
                secondName = dat.getValueByKey("secondName").toString()
                photoUrl = dat.getValueByKey("photoUrl").toString()
                placeOfBirth = dat.getValueByKey("placeOfBirth").toString()
                description = dat.getValueByKey("description").toString()

                if (dat.field == "birthday") {
                    birthday = dat.value.toString().toLong()
                } else if (dat.field == "deathDay") {
                    deathDay = dat.value.toString().toLong()
                }
            }
        }

        val human: HumanInfluxModel? = if (id != null) {
            HumanInfluxModel (
                id = id,
                firstName = firstName,
                secondName = secondName,
                photoUrl = photoUrl,
                birthday = LocalDate.ofInstant(Instant.ofEpochSecond(birthday), ZoneId.systemDefault()),
                placeOfBirth = placeOfBirth,
                deathDay = convertEpochTimeToDate(deathDay),
                description = description,
                functions = HumanInfluxModel.FunctionsValueInflux(
                    director = emptyList(),
                    actor = emptyList()
                )
            )
        } else {
            null
        }

        return human
    }

    private fun convertEpochTimeToDate(deathDay: Long?): LocalDate? {
        return deathDay?.let {
            LocalDate.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
        }
    }
}