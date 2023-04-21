package com.pk.zbtz.zbtzbackend.controllers.people

import com.pk.zbtz.zbtzbackend.controllers.MovieDatabase
import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.controllers.people.service.PeopleServiceFactory
import com.pk.zbtz.zbtzbackend.domain.Human
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class PeopleController(
    private val peopleServiceFactory: PeopleServiceFactory,
) {

    @GetMapping("/{movieDatabase}/people")
    fun getPeople(
        @PathVariable(value = "movieDatabase") movieDatabase: MovieDatabase,
        @RequestParam(value = "searchText", required = false) nameToSearch: String?,
        @RequestParam(value = "pageSize", required = false) pageSize: Int?,
        @RequestParam(value = "offset", required = false) offset: Int?,
    ): ResponseWithStatistics<GetPeopleResponse> =
        peopleServiceFactory
            .create(movieDatabase)
            .getAll(
                nameToSearch = nameToSearch,
                pageSize = pageSize,
                offset = offset,
            )

    @GetMapping("/{movieDatabase}/people/{humanId}")
    fun getHuman(
        @PathVariable(value = "movieDatabase") movieDatabase: MovieDatabase,
        @PathVariable("humanId") humanId: Long,
    ): ResponseWithStatistics<Human> =
        peopleServiceFactory
            .create(movieDatabase)
            .get(humanId = humanId)

    @PostMapping("/{movieDatabase}/people")
    fun addHuman(
        @PathVariable(value = "movieDatabase") movieDatabase: MovieDatabase,
        @RequestBody request: AddHumanRequest,
    ): ResponseWithStatistics<Human> =
        peopleServiceFactory
            .create(movieDatabase)
            .add(request = request)

    @DeleteMapping("{movieDatabase}/people/{humanId}")
    fun deleteHuman(
        @PathVariable(value = "movieDatabase") movieDatabase: MovieDatabase,
        @PathVariable("humanId") humanId: Long,
    ): ResponseWithStatistics<Unit> =
        peopleServiceFactory
            .create(movieDatabase)
            .delete(humanId = humanId)
}