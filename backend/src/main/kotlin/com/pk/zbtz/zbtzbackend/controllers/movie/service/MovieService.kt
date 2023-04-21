package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.domain.Movie

interface MovieService {
    fun getAll(
        sort: GetMoviesSorting?,
        sortingOrder: GetMoviesSortingOrder?,
        title: String?,
        platformName: String?,
        actorId: Long?,
        genreId: Long?,
        directorId: Long?,
        minYear: Int?,
        maxYear: Int?,
        minRating: Float?,
        maxRating: Float?
    ): ResponseWithStatistics<List<Movie>>

    fun get(
        movieId: Long,
    ): ResponseWithStatistics<Movie>

    fun add(
        request: AddMovieRequest
    ): ResponseWithStatistics<Movie>

    fun delete(
        movieId: Long
    ): ResponseWithStatistics<Unit>
}