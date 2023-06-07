package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.databases.influx.models.HumanInfluxModel
import com.pk.zbtz.zbtzbackend.databases.influx.providers.InfluxMemorySizeProvider
import com.pk.zbtz.zbtzbackend.databases.influx.queries.InfluxClient
import com.pk.zbtz.zbtzbackend.databases.influx.queries.InfluxPeopleQueries
import com.pk.zbtz.zbtzbackend.domain.Human
import com.pk.zbtz.zbtzbackend.domain.Statistics
import com.pk.zbtz.zbtzbackend.utils.execution_timer.ExecutionTimer
import org.springframework.stereotype.Service

@Service
class InfluxPeopleService(
    private val influxQueries: InfluxPeopleQueries,
    private val executionTimer: ExecutionTimer,
    private val influxMemorySizeProvider: InfluxMemorySizeProvider,
    ) : PeopleService {
    override fun getAll(
        nameToSearch: String?,
        pageSize: Int?,
        offset: Int?
    ): ResponseWithStatistics<GetPeopleResponse> {
        TODO("Not yet implemented")
    }

    override fun get(humanId: String): ResponseWithStatistics<Human> {
        val influxClient = InfluxClient().create()
        val elapsedTimeResult = executionTimer.measure {
            influxQueries.influxGetHuman(influxClient, humanId)?.toHuman()
        }
        val human = elapsedTimeResult.blockResult
        influxClient.close()

        return ResponseWithStatistics(
            data = human,
            statistics = getStatistics(elapsedTimeResult),
        )
    }
    private fun HumanInfluxModel.toHuman(): Human {
        return Human(
            id = this.id ?: "",
            firstName = this.firstName,
            secondName = this.secondName,
            photoUrl = this.photoUrl,
            birthday = this.birthday,
            placeOfBirth = this.placeOfBirth,
            deathDay = this.deathDay,
            description = this.description,
            functions = this.functions.toFunctionsValue()
        )
    }
    private fun HumanInfluxModel.FunctionsValueInflux.toFunctionsValue(): Human.FunctionsValue =
        Human.FunctionsValue(
            director = this.director.map { it.toDirector() },
            actor = this.actor.map { it.toActor() }
        )

    private fun HumanInfluxModel.FunctionsValueInflux.FunctionInflux.DirectorInflux.toDirector(): Human.FunctionsValue.Function.Director =
        Human.FunctionsValue.Function.Director(
            filmId = this.filmId,
            title = this.title
        )

    private fun HumanInfluxModel.FunctionsValueInflux.FunctionInflux.ActorInflux.toActor(): Human.FunctionsValue.Function.Actor =
        Human.FunctionsValue.Function.Actor(
            filmId = this.filmId,
            title = this.title
        )
    override fun add(request: AddHumanRequest): ResponseWithStatistics<Human> {
        val influxClient = InfluxClient().create()
        val elapsedTimeResult = executionTimer.measure {
            influxQueries.influxAddHuman(influxClient, request)
        }
        val human = elapsedTimeResult.blockResult.toHuman()
        influxClient.close()

        return ResponseWithStatistics(
            data = human,
            statistics = getStatistics(elapsedTimeResult),
        )
    }


    override fun delete(humanId: String): ResponseWithStatistics<Unit> {
        TODO("Not yet implemented")
    }

    private fun <T> getStatistics(
        elapsedTimeResult: ExecutionTimer.ElapsedTimeResult<T>
    ): Statistics =
        Statistics(
            accessTime = elapsedTimeResult.time,
            databaseMemorySize = influxMemorySizeProvider.getDatabaseSizeInGigabytes(),
        )

}
