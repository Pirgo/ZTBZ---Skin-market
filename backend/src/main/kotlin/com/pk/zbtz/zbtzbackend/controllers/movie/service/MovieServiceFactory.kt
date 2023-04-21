package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.MovieDatabase
import org.springframework.stereotype.Component

@Component
class MovieServiceFactory {

    fun create(movieDatabase: MovieDatabase): MovieService =
        when(movieDatabase) {
            MovieDatabase.POSTGRESQL -> TODO("POSTGRESQL is not implemented yet")
            MovieDatabase.MONGO_DB -> TODO("MONGO_DB is not implemented yet")
            MovieDatabase.INFLUX -> TODO("INFLUX is not implemented yet")
        }
}