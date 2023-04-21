package com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses

import com.pk.zbtz.zbtzbackend.domain.MovieSummary

data class GetMoviesResponse(
    val movies: List<MovieSummary>,
    val nextOffset: Int,
    val totalPages: Int,
    val totalRecords: Int,
)
