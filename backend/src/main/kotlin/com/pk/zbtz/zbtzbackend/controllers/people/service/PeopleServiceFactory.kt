package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.controllers.MovieDatabase
import org.springframework.stereotype.Component

@Component
class PeopleServiceFactory(
    private val fakePeopleService: FakePeopleService,
    private val mongoPeopleService: MongoPeopleService,
    private val influxPeopleService: InfluxPeopleService,

) {

    fun create(movieDatabase: MovieDatabase): PeopleService =
        when(movieDatabase) {
            MovieDatabase.POSTGRESQL -> TODO("POSTGRESQL is not implemented yet")
            MovieDatabase.MONGO_DB -> mongoPeopleService
            MovieDatabase.INFLUX -> influxPeopleService
            MovieDatabase.FAKE_DATABASE -> fakePeopleService
        }
}
