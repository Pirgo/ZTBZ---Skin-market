package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesResponse
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.domain.Movie

@Service
class InfluxMovieService() : MovieService {
    override fun getAll(
        sort: GetMoviesSorting?,
        sortingOrder: GetMoviesSortingOrder?,
        titleToSearch: String?,
        platformName: String?,
        year: Int?,
        pageSize: Int?,
        offset: Int?
    ): ResponseWithStatistics<GetMoviesResponse> {
        TODO("Not yet implemented")
    }

    override fun get(movieId: String): ResponseWithStatistics<Movie> {
        TODO("Not yet implemented")
    }

    override fun add(request: AddMovieRequest): ResponseWithStatistics<Movie> {
        TODO("Not yet implemented")
    }

    override fun delete(movieId: String): ResponseWithStatistics<Unit> {
        TODO("Not yet implemented")
    }
}
