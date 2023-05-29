package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.services.HumanService
import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.controllers.people.service.PeopleService
import com.pk.zbtz.zbtzbackend.domain.Human
import com.pk.zbtz.zbtzbackend.domain.Statistics
import com.pk.zbtz.zbtzbackend.utils.execution_timer.ExecutionTimer
import org.springframework.stereotype.Service

@Service
class PostgresPeopleService(
    private val service: HumanService,
    private val executionTimer: ExecutionTimer,

    ): PeopleService {
    override fun getAll(
        nameToSearch: String?,
        pageSize: Int?,
        offset: Int?,
    ): ResponseWithStatistics<GetPeopleResponse> {
        TODO("Not yet implemented")
    }

    override fun get(humanId: String): ResponseWithStatistics<Human> {
        val elapsedTimeResult = executionTimer.measure {
            service.getById(humanId.toInt())?.toHuman()
        }
        val human = elapsedTimeResult.blockResult
        return ResponseWithStatistics(
            data = human,
            statistics = getStatistics(elapsedTimeResult)
        )
    }

    override fun add(request: AddHumanRequest): ResponseWithStatistics<Human> {
        val elapsedTimeResult = executionTimer.measure {
            service.save(request.toPostgresHumanModel())
        }

        val human = elapsedTimeResult.blockResult.toHuman()
        return ResponseWithStatistics(
            data = human,
            statistics = getStatistics(elapsedTimeResult)
        )
    }

    override fun delete(humanId: String): ResponseWithStatistics<Unit> {
        val elapsedTimeResult = executionTimer.measure {
            service.delete(humanId.toInt())
        }

        return ResponseWithStatistics(
            statistics = getStatistics(elapsedTimeResult)
        )
    }

    private fun <T> getStatistics(
        elapsedTimeResult: ExecutionTimer.ElapsedTimeResult<T>
    ): Statistics =
        Statistics(
            accessTime = elapsedTimeResult.time,
            //TODO get db size
            databaseMemorySize = 1000.0,
        )

}