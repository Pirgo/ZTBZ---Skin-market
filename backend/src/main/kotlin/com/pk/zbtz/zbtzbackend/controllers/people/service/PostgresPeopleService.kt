package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories.HumanPostgresRepository
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.services.HumanService
import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.controllers.people.service.PeopleService
import com.pk.zbtz.zbtzbackend.domain.Human
import com.pk.zbtz.zbtzbackend.domain.HumanSummary
import com.pk.zbtz.zbtzbackend.domain.Statistics
import com.pk.zbtz.zbtzbackend.utils.execution_timer.ExecutionTimer
import org.springframework.stereotype.Service
import kotlin.math.ceil

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
        val offset = offset ?: DEFAULT_OFFSET
        val pageSize = pageSize ?: DEFAULT_PAGE_SIZE
        val elapsedTimeResult = executionTimer.measure {
            if(nameToSearch.isNullOrEmpty()){
                service.getAll(offset * pageSize, pageSize)
            } else {
                service.getAllByFirstNameOrSecondName(nameToSearch, offset * pageSize, pageSize)
            }
        }

        val peoplePage = elapsedTimeResult.blockResult
        val peopleSummary = peoplePage.map { it.toHumanSummary() }
        val statistics = getStatistics(elapsedTimeResult)

        val response = GetPeopleResponse(
            people = peopleSummary,
            nextOffset = offset + 1,
            totalPages = 100, //TODO change after filling db
            totalRecords = 100 //TODO change after filling db
        )
        return ResponseWithStatistics(
            data = response,
            statistics = statistics
        )
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

    private fun limitResult(people: List<HumanSummary>, limit: Int): List<HumanSummary> {
        return people.take(limit)
    }

    private fun calculateTotalPages(people: List<HumanSummary>, pageSize: Int): Int {
        return ceil(people.size.toDouble() / pageSize).toInt()
    }

    private companion object {
        const val DEFAULT_OFFSET = 0
        const val DEFAULT_PAGE_SIZE = 10
    }
}