package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.databases.mondodb.models.HumanMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.models.MovieMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.providers.MongoMemorySizeProvider
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.HumanMongoRepository
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.MovieMongoRepository
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
    private val movieMongoRepository: MovieMongoRepository,
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
        val elapsedTimeResult = executionTimer.measure {
            request
                .toHumanMongoModel()
                .let(repository::save)
                .also(::updateMoviesWithNewHuman)
        }
        val human = elapsedTimeResult.blockResult.toHuman()

        return ResponseWithStatistics(
            data = human,
            statistics = getStatistics(elapsedTimeResult),
        )
    }

    private fun AddHumanRequest.toHumanMongoModel(): HumanMongoModel {
        val movieIds = this.functions.director.map { it.filmId } + this.functions.actor.map { it.filmId }
        val movies = movieMongoRepository.findAllById(movieIds)

        val directorFunctions = this.functions.director.map { director ->
            val movie = movies.find { it.id == director.filmId }
            HumanMongoModel.FunctionsValueMongo.FunctionMongo.DirectorMongo(
                filmId = director.filmId,
                title = movie?.title ?: ""
            )
        }

        val actorFunctions = this.functions.actor.map { actor ->
            val movie = movies.find { it.id == actor.filmId }
            HumanMongoModel.FunctionsValueMongo.FunctionMongo.ActorMongo(
                filmId = actor.filmId,
                title = movie?.title ?: ""
            )
        }

        return HumanMongoModel(
            firstName = this.firstName,
            secondName = this.secondName,
            photoUrl = this.photoUrl,
            birthday = this.birthday,
            placeOfBirth = this.placeOfBirth,
            deathDay = this.deathDay,
            description = this.description,
            functions = HumanMongoModel.FunctionsValueMongo(
                director = directorFunctions,
                actor = actorFunctions
            )
        )
    }

    private fun updateMoviesWithNewHuman(human: HumanMongoModel) {
        val directorMovies = human.functions.director.map { it.filmId }
        val actorMovies = human.functions.actor.map { it.filmId }
        val movieIds = directorMovies + actorMovies

        movieMongoRepository
            .findAllById(movieIds)
            .forEach { movie ->
                val updatedDirectors = when (directorMovies.contains(movie.id)) {
                    true -> movie.directors + MovieMongoModel.HumanMovieMongo.Director(
                        id = human.id,
                        name = "${human.firstName} ${human.secondName}",
                        photoUrl = human.photoUrl.orEmpty()
                    )

                    false -> movie.directors
                }

                val updatedActors = when (actorMovies.contains(movie.id)) {
                    true -> movie.actors + MovieMongoModel.HumanMovieMongo.Actor(
                        id = human.id,
                        name = "${human.firstName} ${human.secondName}",
                        photoUrl = human.photoUrl.orEmpty(),
                        character = ""      // TODO: Should it be like this?
                    )

                    false -> movie.actors
                }

                movie
                    .copy(
                        actors = updatedActors,
                        directors = updatedDirectors,
                    )
                    .let(movieMongoRepository::save)
            }
    }

    override fun delete(humanId: String): ResponseWithStatistics<Unit> {
        val elapsedTimeResult = executionTimer.measure {
            repository.deleteById(humanId)
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
            databaseMemorySize = mongoMemorySizeProvider.getDatabaseSizeInGigabytes(),
        )
}
