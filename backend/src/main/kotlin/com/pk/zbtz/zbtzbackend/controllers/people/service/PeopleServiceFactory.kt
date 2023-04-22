package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.controllers.MovieDatabase
import org.springframework.stereotype.Component

@Component
class PeopleServiceFactory(
    private val fakePeopleService: FakePeopleService,
) {

    fun create(movieDatabase: MovieDatabase): PeopleService =
        when(movieDatabase) {
            MovieDatabase.POSTGRESQL -> TODO("POSTGRESQL is not implemented yet")
            MovieDatabase.MONGO_DB -> TODO("MONGO_DB is not implemented yet")
            MovieDatabase.INFLUX -> TODO("INFLUX is not implemented yet")
            MovieDatabase.FAKE_DATABASE -> fakePeopleService
        }
}