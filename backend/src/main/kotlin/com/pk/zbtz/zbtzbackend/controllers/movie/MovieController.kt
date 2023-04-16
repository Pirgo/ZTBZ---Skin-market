package com.pk.zbtz.zbtzbackend.controllers.movie

import com.pk.zbtz.zbtzbackend.controllers.Response
import com.pk.zbtz.zbtzbackend.domain.Movie
import com.pk.zbtz.zbtzbackend.domain.Statistics
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/movies")
class MovieController {

    @GetMapping
    fun getMovies(
        @RequestParam(value = "sort", required = false) sort: GetMoviesSorting?,
        @RequestParam(value = "sorting_order", required = false) sortingOrder: GetMoviesSortingOrder?,
        @RequestParam(value = "genre_id", required = false) genreId: Long?,
        @RequestParam(value = "director_id", required = false) directorId: Long?,
        @RequestParam(value = "min_year", required = false) minYear: Int?,
        @RequestParam(value = "max_year", required = false) maxYear: Int?,
        @RequestParam(value = "min_rating", required = false) minRating: Float?,
        @RequestParam(value = "max_rating", required = false) maxRating: Float?
    ): Response<List<Movie>> {
        // TODO: Implement

        println(sort)

        return Response(
            data = emptyList(),
            statistics = Statistics()
        )
    }

    @GetMapping("/{movie_id}")
    fun getMovie(
        @PathVariable("movie_id") movieId: Long,
    ): Response<Movie> {
        // TODO: Implement

        return Response(
            data = null,
            statistics = Statistics()
        )
    }

    @PostMapping
    fun addMovie(
        @RequestBody request: AddMovieRequest
    ): Response<Movie> {
        // TODO: Implement

        return Response(
            data = null,
            statistics = Statistics()
        )
    }

    @DeleteMapping("/{movie_id}")
    fun deleteMovie(
        @PathVariable("movie_id") movieId: Long,
    ): Response<Any> {
        // TODO: Implement

        return Response(
            data = null,
            statistics = Statistics()
        )
    }
}