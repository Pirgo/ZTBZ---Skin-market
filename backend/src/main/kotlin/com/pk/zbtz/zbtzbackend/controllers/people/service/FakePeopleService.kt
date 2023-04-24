package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.domain.Human
import com.pk.zbtz.zbtzbackend.domain.HumanSummary
import com.pk.zbtz.zbtzbackend.domain.Statistics
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.math.ceil

/*
* Only for testing purpose. Functions return dummy data to imitate API usage.
*/
@Service
class FakePeopleService : PeopleService {
    private val dummyHumans = dummyPeople.toMutableList()

    override fun getAll(
        nameToSearch: String?,
        pageSize: Int?,
        offset: Int?
    ): ResponseWithStatistics<GetPeopleResponse> {
        var filteredHumans = dummyHumans.toList()

        // Apply filter
        nameToSearch?.let { search ->
            filteredHumans = filteredHumans.filter { human ->
                human.firstName.contains(search, ignoreCase = true) ||
                        human.secondName.contains(search, ignoreCase = true)
            }
        }

        // Apply pagination
        val offsetValue = offset ?: 0
        val pageSizeValue = pageSize ?: 10
        val paginatedHumans = filteredHumans.drop(offsetValue).take(pageSizeValue)
        val humanSummaries = paginatedHumans.map { human ->
            HumanSummary(
                id = human.id,
                firstName = human.firstName,
                secondName = human.secondName,
                photoUrl = human.photoUrl
            )
        }

        val response = GetPeopleResponse(
            people = humanSummaries,
            nextOffset = if (offsetValue + pageSizeValue < filteredHumans.size) offsetValue + pageSizeValue else null,
            totalPages = ceil(filteredHumans.size.toFloat() / pageSizeValue).toInt(),
            totalRecords = filteredHumans.size
        )

        return ResponseWithStatistics(
            data = response,
            statistics = dummyStatistics
        )
    }

    override fun get(humanId: String): ResponseWithStatistics<Human> {
        val human = dummyHumans.find { it.id == humanId }

        return ResponseWithStatistics(
            data = human,
            statistics = dummyStatistics
        )
    }

    override fun add(request: AddHumanRequest): ResponseWithStatistics<Human> {
        val newId = dummyHumans.maxOf { it.id } + 1
        val newHuman = Human(
            id = newId,
            firstName = request.firstName,
            secondName = request.secondName,
            photoUrl = request.photoUrl,
            birthday = request.birthday,
            placeOfBirth = request.placeOfBirth,
            deathDay = request.deathDay,
            description = request.description,
            functions = Human.FunctionsValue(
                director = request.functions.director.map { function ->
                    Human.FunctionsValue.Function.Director(
                        filmId = function.filmId,
                        title = "Movie ${function.filmId}"
                    )
                },
                actor = request.functions.actor.map { function ->
                    Human.FunctionsValue.Function.Actor(
                        filmId = function.filmId,
                        title = "Movie ${function.filmId}",
                    )
                }
            )
        )

        dummyHumans.add(newHuman)

        return ResponseWithStatistics(
            data = newHuman,
            statistics = dummyStatistics,
        )
    }

    override fun delete(humanId: String): ResponseWithStatistics<Unit> {
        val human = dummyHumans.find { it.id == humanId }

        dummyHumans.remove(human)

        return ResponseWithStatistics(
            statistics = dummyStatistics
        )
    }

    private companion object {
        val dummyPeople = listOf(
            Human(
                id = "1",
                firstName = "John",
                secondName = "Doe",
                photoUrl = "https://image.tmdb.org/t/p/w500/pVHspL9QWsXCm3t3VXEaCJ9y8Zz.jpg",
                birthday = LocalDate.of(1980, 1, 1),
                placeOfBirth = "New York, USA",
                deathDay = null,
                description = "John Doe is an American actor and director.",
                functions = Human.FunctionsValue(
                    director = listOf(
                        Human.FunctionsValue.Function.Director(filmId = "1", title = "Movie 1")
                    ),
                    actor = listOf(
                        Human.FunctionsValue.Function.Actor(filmId = "1", title = "Movie 1"),
                        Human.FunctionsValue.Function.Actor(filmId = "2", title = "Movie 2")
                    )
                )
            ),
            Human(
                id = "2",
                firstName = "Jane",
                secondName = "Doe",
                photoUrl = "https://image.tmdb.org/t/p/w500/usR5NspRlWgXKUe9qVsCtTCpe4l.jpg",
                birthday = LocalDate.of(1985, 3, 15),
                placeOfBirth = "Los Angeles, USA",
                deathDay = null,
                description = "Jane Doe is an American actress.",
                functions = Human.FunctionsValue(
                    director = emptyList(),
                    actor = listOf(
                        Human.FunctionsValue.Function.Actor(filmId = "2", title = "Movie 2"),
                        Human.FunctionsValue.Function.Actor(filmId = "3", title = "Movie 3")
                    )
                )
            ),
            Human(
                id = "3",
                firstName = "Michael",
                secondName = "Smith",
                photoUrl = "https://image.tmdb.org/t/p/w500/usR5NspRlWgXKUe9qVsCtTCpe4l.jpg",
                birthday = LocalDate.of(1975, 6, 30),
                placeOfBirth = "Chicago, USA",
                deathDay = null,
                description = "Michael Smith is an American director and producer.",
                functions = Human.FunctionsValue(
                    director = listOf(
                        Human.FunctionsValue.Function.Director(filmId = "3", title = "Movie 3"),
                        Human.FunctionsValue.Function.Director(filmId = "4", title = "Movie 4")
                    ),
                    actor = emptyList()
                )
            ),
            Human(
                id = "4",
                firstName = "Emma",
                secondName = "Brown",
                photoUrl = "https://image.tmdb.org/t/p/w500/usR5NspRlWgXKUe9qVsCtTCpe4l.jpg",
                birthday = LocalDate.of(1990, 7, 22),
                placeOfBirth = "London, UK",
                deathDay = null,
                description = "Emma Brown is a British actress and model.",
                functions = Human.FunctionsValue(
                    director = emptyList(),
                    actor = listOf(
                        Human.FunctionsValue.Function.Actor(filmId = "4", title = "Movie 4"),
                        Human.FunctionsValue.Function.Actor(filmId = "5", title = "Movie 5")
                    )
                )
            ),
            Human(
                id = "5",
                firstName = "David",
                secondName = "Johnson",
                photoUrl = "https://image.tmdb.org/t/p/w500/usR5NspRlWgXKUe9qVsCtTCpe4l.jpg",
                birthday = LocalDate.of(1965, 11, 5),
                placeOfBirth = "Sydney, Australia",
                deathDay = null,
                description = "David Johnson is an Australian actor and director.",
                functions = Human.FunctionsValue(
                    director = listOf(
                        Human.FunctionsValue.Function.Director(filmId = "5", title = "Movie 5")
                    ),
                    actor = listOf(
                        Human.FunctionsValue.Function.Actor(filmId = "1", title = "Movie 1"),
                        Human.FunctionsValue.Function.Actor(filmId = "3", title = "Movie 3"),
                        Human.FunctionsValue.Function.Actor(filmId = "5", title = "Movie 5")
                    )
                )
            )
        )

        val dummyStatistics = Statistics(
            accessTime = 1000,
            databaseMemorySize = 2137,
        )
    }
}