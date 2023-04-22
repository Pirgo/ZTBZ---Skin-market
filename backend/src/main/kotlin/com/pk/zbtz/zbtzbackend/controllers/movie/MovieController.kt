package com.pk.zbtz.zbtzbackend.controllers.movie

import com.pk.zbtz.zbtzbackend.controllers.MovieDatabase
import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesResponse
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.controllers.movie.service.MovieServiceFactory
import com.pk.zbtz.zbtzbackend.domain.Movie
import com.pk.zbtz.zbtzbackend.domain.MovieSummary
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class MovieController(
    private val movieServiceFactory: MovieServiceFactory
) {

    @GetMapping("/{movieDatabase}/movies")
    fun getMovies(
        @PathVariable(value = "movieDatabase") movieDatabase: MovieDatabase,
        @RequestParam(value = "sort", required = false) sort: GetMoviesSorting?,
        @RequestParam(value = "sorting_order", required = false) sortingOrder: GetMoviesSortingOrder? = GetMoviesSortingOrder.ASC,
        @RequestParam(value = "searchText", required = false) titleToSearch: String?,
        @RequestParam(value = "platform", required = false) platformName: String?,
        @RequestParam(value = "year", required = false) year: Int?,
        @RequestParam(value = "pageSize", required = false) pageSize: Int?,
        @RequestParam(value = "offset", required = false) offset: Int?,
    ): ResponseWithStatistics<GetMoviesResponse> =
        movieServiceFactory
            .create(movieDatabase)
            .getAll(
                sort = sort,
                sortingOrder = sortingOrder,
                titleToSearch = titleToSearch,
                platformName = platformName,
                year = year,
                pageSize = pageSize,
                offset = offset,
            )

    @GetMapping("/{movieDatabase}/movies/{movieId}")
    fun getMovie(
        @PathVariable(value = "movieDatabase") movieDatabase: MovieDatabase,
        @PathVariable("movieId") movieId: Long,
    ): ResponseWithStatistics<Movie> =
        movieServiceFactory
            .create(movieDatabase)
            .get(movieId = movieId)

    @PostMapping("/{movieDatabase}/movies")
    fun addMovie(
        @PathVariable(value = "movieDatabase") movieDatabase: MovieDatabase,
        @RequestBody request: AddMovieRequest
    ): ResponseWithStatistics<Movie> =
        movieServiceFactory
            .create(movieDatabase)
            .add(request = request)

    @DeleteMapping("/{movieDatabase}/movies/{movieId}")
    fun deleteMovie(
        @PathVariable(value = "movieDatabase") movieDatabase: MovieDatabase,
        @PathVariable("movieId") movieId: Long,
    ): ResponseWithStatistics<Unit> =
        movieServiceFactory
            .create(movieDatabase)
            .delete(movieId = movieId)
}