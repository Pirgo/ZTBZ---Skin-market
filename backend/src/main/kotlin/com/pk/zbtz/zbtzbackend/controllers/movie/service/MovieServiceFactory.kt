package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.MovieDatabase
import org.springframework.stereotype.Component

@Component
class MovieServiceFactory(
    private val mongoMovieService: MongoMovieService,
    private val fakeMovieService: FakeMovieService,
    private val influxMovieService: InfluxMovieService,
) {

    fun create(movieDatabase: MovieDatabase): MovieService =
        when(movieDatabase) {
            MovieDatabase.POSTGRESQL -> TODO("POSTGRESQL is not implemented yet")
            MovieDatabase.MONGO_DB -> mongoMovieService
            MovieDatabase.INFLUX -> influxMovieService
            MovieDatabase.FAKE_DATABASE -> fakeMovieService
        }
}
