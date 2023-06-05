package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesResponse
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.controllers.movie.service.MovieService
import com.pk.zbtz.zbtzbackend.databases.postgres.models.MoviePostgresModel
import com.pk.zbtz.zbtzbackend.domain.Movie
import com.pk.zbtz.zbtzbackend.domain.MovieSummary
import com.pk.zbtz.zbtzbackend.domain.Statistics
import com.pk.zbtz.zbtzbackend.utils.execution_timer.ExecutionTimer
import org.springframework.stereotype.Service
import kotlin.math.ceil

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
        val offset = offset ?: DEFAULT_OFFSET
        val pageSize = pageSize ?: DEFAULT_PAGE_SIZE

        //without sorting
        //narzut tych ifow ale jebac
        val elapsedTimeResult = executionTimer.measure {
            if(year != null && !platformName.isNullOrEmpty() && !titleToSearch.isNullOrEmpty()){
                service.getAllByPlatformAndTitleAndYear(platformName, titleToSearch, year, offset * pageSize, pageSize)
            } else if (year != null && !platformName.isNullOrEmpty()) {
                service.getAllByPlatformAndYear(platformName, year, offset * pageSize, pageSize)
            } else if (year != null && !titleToSearch.isNullOrEmpty()) {
                service.getAllByTitleAndYear(titleToSearch, year, offset * pageSize, pageSize)
            } else if (!platformName.isNullOrEmpty() && !titleToSearch.isNullOrEmpty()) {
                service.getAllByTitleAndPlatform(titleToSearch, platformName, offset * pageSize, pageSize)
            } else if (year != null) {
                service.getAllByYear(year, offset * pageSize, pageSize)
            } else if (!platformName.isNullOrEmpty()) {
                service.getAllByPlatform(platformName, offset * pageSize, pageSize)
            } else if(!titleToSearch.isNullOrEmpty()){
                service.getAllByTitle(titleToSearch, offset * pageSize, pageSize)
            }
            else {
                service.getAll(offset * pageSize, pageSize)
            }
        }

        val moviesPage: List<MoviePostgresModel> = elapsedTimeResult.blockResult
        val movieSummary = moviesPage.map { it.toMovieSummary() }
        val statistics = getStatistics(elapsedTimeResult)

        val response = GetMoviesResponse(
            movies = movieSummary,
            nextOffset = offset + 1,
            totalPages = 100, //TODO change after filling db
            totalRecords = 100 //TODO change after filling db
        )

        return ResponseWithStatistics(
            data = response,
            statistics = statistics
        )
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

    private fun calculateTotalPages(movies: List<MovieSummary>, pageSize: Int): Int {
        return ceil(movies.size.toDouble() / pageSize).toInt()
    }

    private companion object {
        const val DEFAULT_OFFSET = 0
        const val DEFAULT_PAGE_SIZE = 10
    }
}