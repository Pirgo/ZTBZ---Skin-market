package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.controllers.people.service.PostgresPeopleService
import com.pk.zbtz.zbtzbackend.controllers.MovieDatabase
import org.springframework.stereotype.Component

@Component
class PeopleServiceFactory(
    private val fakePeopleService: FakePeopleService,
    private val mongoPeopleService: MongoPeopleService,
    private val postgresPeopleService: PostgresPeopleService
) {

    fun create(movieDatabase: MovieDatabase): PeopleService =
        when(movieDatabase) {
            MovieDatabase.POSTGRESQL -> postgresPeopleService
            MovieDatabase.MONGO_DB -> mongoPeopleService
            MovieDatabase.INFLUX -> TODO("INFLUX is not implemented yet")
            MovieDatabase.FAKE_DATABASE -> fakePeopleService
        }
}