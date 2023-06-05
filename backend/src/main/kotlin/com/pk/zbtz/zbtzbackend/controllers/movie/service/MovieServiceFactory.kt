package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.controllers.movie.service.PostgresMovieService
import com.pk.zbtz.zbtzbackend.controllers.MovieDatabase
import org.springframework.stereotype.Component

@Component
class MovieServiceFactory(
    private val mongoMovieService: MongoMovieService,
    private val fakeMovieService: FakeMovieService,
    private val postgresMovieService: PostgresMovieService
) {

    fun create(movieDatabase: MovieDatabase): MovieService =
        when(movieDatabase) {
            MovieDatabase.POSTGRESQL -> postgresMovieService
            MovieDatabase.MONGO_DB -> mongoMovieService
            MovieDatabase.INFLUX -> TODO("INFLUX is not implemented yet")
            MovieDatabase.FAKE_DATABASE -> fakeMovieService
        }
}