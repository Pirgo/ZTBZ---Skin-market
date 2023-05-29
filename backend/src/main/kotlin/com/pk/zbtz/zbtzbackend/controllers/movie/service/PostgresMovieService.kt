package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesResponse
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.controllers.movie.service.MovieService
import com.pk.zbtz.zbtzbackend.domain.Movie
import com.pk.zbtz.zbtzbackend.domain.Statistics
import com.pk.zbtz.zbtzbackend.utils.execution_timer.ExecutionTimer
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class PostgresMovieService(
    private val executionTimer: ExecutionTimer,
    private val service: com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.services.MovieService
): MovieService {
    override fun getAll(
        sort: GetMoviesSorting?,
        sortingOrder: GetMoviesSortingOrder?,
        titleToSearch: String?,
        platformName: String?,
        year: Int?,
        pageSize: Int?,
        offset: Int?,
    ): ResponseWithStatistics<GetMoviesResponse> {
        TODO("Not yet implemented")
    }

    override fun get(movieId: String): ResponseWithStatistics<Movie> {
        val elapsedTimeResult = executionTimer.measure {
            service.getById(movieId.toInt())?.toMovie()
        }
        val movie = elapsedTimeResult.blockResult

        return ResponseWithStatistics(
            data = movie,
            statistics = getStatistics(elapsedTimeResult)
        )
    }

    override fun add(request: AddMovieRequest): ResponseWithStatistics<Movie> {
        val elapsedTimeResult = executionTimer.measure {
            service.save(request).toMovie()
        }
        val movie = elapsedTimeResult.blockResult

        return ResponseWithStatistics(
            data = movie,
            statistics = getStatistics(elapsedTimeResult)
        )
    }

    override fun delete(movieId: String): ResponseWithStatistics<Unit> {
        val elapsedTimeResult = executionTimer.measure {
            service.delete(movieId.toInt())
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
            //TODO fix
            databaseMemorySize = 1000.0,
        )
}