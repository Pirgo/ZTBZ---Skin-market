package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.databases.mondodb.models.HumanMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.providers.MongoMemorySizeProvider
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.HumanMongoRepository
import com.pk.zbtz.zbtzbackend.domain.Human
import com.pk.zbtz.zbtzbackend.domain.HumanSummary
import com.pk.zbtz.zbtzbackend.domain.Statistics
import com.pk.zbtz.zbtzbackend.utils.execution_timer.ExecutionTimer
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class MongoPeopleService(
    private val repository: HumanMongoRepository,
    private val executionTimer: ExecutionTimer,
    private val mongoMemorySizeProvider: MongoMemorySizeProvider,
) : PeopleService {
    override fun getAll(
        nameToSearch: String?,
        pageSize: Int?,
        offset: Int?
    ): ResponseWithStatistics<GetPeopleResponse> {
        val pageRequest = PageRequest.of(offset ?: 0, pageSize ?: 10)

        val elapsedTimeResult = executionTimer.measure {
            repository.findByFirstNameOrSecondName(
                name = nameToSearch.orEmpty(),
                pageable = pageRequest,
            )
        }

        val peoplePage = elapsedTimeResult.blockResult

        val peopleSummary = peoplePage.content.map { it.toHumanSummary() }
        val nextOffset = calculateNextOffset(peoplePage, offset, pageSize)
        val response = GetPeopleResponse(
            people = peopleSummary,
            nextOffset = nextOffset,
            totalPages = peoplePage.totalPages,
            totalRecords = peoplePage.totalElements.toInt(),
        )

        val statistics = getStatistics(elapsedTimeResult)

        return ResponseWithStatistics(
            data = response,
            statistics = statistics,
        )
    }

    private fun HumanMongoModel.toHumanSummary(): HumanSummary =
        HumanSummary(
            id = this.id ?: "",
            firstName = this.firstName,
            secondName = this.secondName,
            photoUrl = this.photoUrl
        )

    private fun calculateNextOffset(
        moviesPage: Page<HumanMongoModel>,
        offset: Int?,
        pageSize: Int?
    ): Int? = if (moviesPage.hasNext()) (offset ?: 0) + (pageSize ?: 10) else null


    @OptIn(ExperimentalStdlibApi::class)
    override fun get(humanId: String): ResponseWithStatistics<Human> {
        val elapsedTimeResult = executionTimer.measure {
            repository
                .findById(humanId)
                .getOrNull()
                ?.toHuman()

        }
        val human = elapsedTimeResult.blockResult

        return ResponseWithStatistics(
            data = human,
            statistics = getStatistics(elapsedTimeResult)
        )
    }

    private fun HumanMongoModel.toHuman(): Human {
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

    private fun HumanMongoModel.FunctionsValueMongo.toFunctionsValue(): Human.FunctionsValue =
        Human.FunctionsValue(
            director = this.director.map { it.toDirector() },
            actor = this.actor.map { it.toActor() }
        )

    private fun HumanMongoModel.FunctionsValueMongo.FunctionMongo.DirectorMongo.toDirector(): Human.FunctionsValue.Function.Director =
        Human.FunctionsValue.Function.Director(
            filmId = this.filmId,
            title = this.title
        )

    private fun HumanMongoModel.FunctionsValueMongo.FunctionMongo.ActorMongo.toActor(): Human.FunctionsValue.Function.Actor =
        Human.FunctionsValue.Function.Actor(
            filmId = this.filmId,
            title = this.title
        )

    override fun add(request: AddHumanRequest): ResponseWithStatistics<Human> {
        TODO("Not yet implemented")
    }

    override fun delete(humanId: String): ResponseWithStatistics<Unit> {
        TODO("Not yet implemented")
    }

    private fun <T> getStatistics(
        elapsedTimeResult: ExecutionTimer.ElapsedTimeResult<T>
    ): Statistics =
        Statistics(
            accessTime = elapsedTimeResult.time,
            databaseMemorySize = mongoMemorySizeProvider.getDatabaseSizeInGigabytes(),
        )
}
