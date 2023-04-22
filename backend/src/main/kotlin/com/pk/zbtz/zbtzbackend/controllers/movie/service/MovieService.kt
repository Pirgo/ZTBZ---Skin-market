package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesResponse
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.domain.Movie

interface MovieService {
    fun getAll(
        sort: GetMoviesSorting?,
        sortingOrder: GetMoviesSortingOrder?,
        titleToSearch: String?,
        platformName: String?,
        year: Int?,
        pageSize: Int?,
        offset: Int?,
    ): ResponseWithStatistics<GetMoviesResponse>

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