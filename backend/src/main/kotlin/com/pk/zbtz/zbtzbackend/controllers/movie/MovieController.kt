package com.pk.zbtz.zbtzbackend.controllers.movie

import com.pk.zbtz.zbtzbackend.controllers.MovieDatabase
import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.service.MovieServiceFactory
import com.pk.zbtz.zbtzbackend.domain.Movie
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class MovieController(
    private val movieServiceFactory: MovieServiceFactory
) {

    @GetMapping("/{movie_database}/movies")
    fun getMovies(
        @PathVariable(value = "movie_database") movieDatabase: MovieDatabase,
        @RequestParam(value = "sort", required = false) sort: GetMoviesSorting?,
        @RequestParam(value = "sorting_order", required = false) sortingOrder: GetMoviesSortingOrder?,
        @RequestParam(value = "title", required = false) title: String?,
        @RequestParam(value = "platform_name", required = false) platformName: String?,
        @RequestParam(value = "actor_id", required = false) actorId: Long?,
        @RequestParam(value = "genre_id", required = false) genreId: Long?,
        @RequestParam(value = "director_id", required = false) directorId: Long?,
        @RequestParam(value = "min_year", required = false) minYear: Int?,
        @RequestParam(value = "max_year", required = false) maxYear: Int?,
        @RequestParam(value = "min_rating", required = false) minRating: Float?,
        @RequestParam(value = "max_rating", required = false) maxRating: Float?
    ): ResponseWithStatistics<List<Movie>> =
        movieServiceFactory
            .create(movieDatabase)
            .getAll(
                sort = sort,
                sortingOrder = sortingOrder,
                title = title,
                platformName = platformName,
                actorId = actorId,
                genreId = genreId,
                directorId = directorId,
                minYear = minYear,
                maxYear = maxYear,
                minRating = minRating,
                maxRating = maxRating,
            )

    @GetMapping("/{movie_database}/movies/{movie_id}")
    fun getMovie(
        @PathVariable(value = "movie_database") movieDatabase: MovieDatabase,
        @PathVariable("movie_id") movieId: Long,
    ): ResponseWithStatistics<Movie> =
        movieServiceFactory
            .create(movieDatabase)
            .get(movieId = movieId)

    @PostMapping("/{movie_database}/movies")
    fun addMovie(
        @PathVariable(value = "movie_database") movieDatabase: MovieDatabase,
        @RequestBody request: AddMovieRequest
    ): ResponseWithStatistics<Movie> =
        movieServiceFactory
            .create(movieDatabase)
            .add(request = request)

    @DeleteMapping("/{movie_database}/movies/{movie_id}")
    fun deleteMovie(
        @PathVariable(value = "movie_database") movieDatabase: MovieDatabase,
        @PathVariable("movie_id") movieId: Long,
    ): ResponseWithStatistics<Unit> =
        movieServiceFactory
            .create(movieDatabase)
            .delete(movieId = movieId)
}