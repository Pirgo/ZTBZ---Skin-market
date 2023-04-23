package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesResponse
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.databases.mondodb.models.MovieMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.MovieMongoRepository
import com.pk.zbtz.zbtzbackend.domain.Genre
import com.pk.zbtz.zbtzbackend.domain.Movie
import com.pk.zbtz.zbtzbackend.domain.MovieSummary
import com.pk.zbtz.zbtzbackend.domain.Statistics
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class MongoMovieService(
    private val repository: MovieMongoRepository,
) : MovieService {
    override fun getAll(
        sort: GetMoviesSorting?,
        sortingOrder: GetMoviesSortingOrder?,
        titleToSearch: String?,
        platformName: String?,
        year: Int?,
        pageSize: Int?,
        offset: Int?
    ): ResponseWithStatistics<GetMoviesResponse> {
        val sortOrder = sortingOrder?.let {
            when (it) {
                GetMoviesSortingOrder.ASC -> Sort.Direction.ASC
                GetMoviesSortingOrder.DESC -> Sort.Direction.DESC
            }
        } ?: Sort.Direction.ASC

        val sortField = sort?.let {
            when (it) {
                GetMoviesSorting.ID -> "id"
                GetMoviesSorting.TITLE -> "title"
                GetMoviesSorting.PRODUCTION_YEAR -> "productionYear"
                GetMoviesSorting.RATING -> "rating"
                GetMoviesSorting.PLATFORM -> "platforms.name" // Assuming that you want to sort by the platform name
                GetMoviesSorting.GENRE -> "genres.name" // Assuming that you want to sort by the genre name
            }
        } ?: "id"

        val pageRequest = PageRequest.of(offset ?: 0, pageSize ?: 10, Sort.by(sortOrder, sortField))

        val moviesPage = when (year) {
            null -> repository.findAllByFilters(titleToSearch.orEmpty(), platformName.orEmpty(), pageRequest)
            else -> repository.findAllByFilters(titleToSearch.orEmpty(), platformName.orEmpty(), year, pageRequest)
        }

        val movieSummaries = moviesPage.content.map { it.toMovieSummary() }

        val nextOffset = if (moviesPage.hasNext()) (offset ?: 0) + (pageSize ?: 10) else null
        val response = GetMoviesResponse(
            movies = movieSummaries,
            nextOffset = nextOffset,
            totalPages = moviesPage.totalPages,
            totalRecords = moviesPage.totalElements.toInt()
        )

        val statistics = Statistics()

        return ResponseWithStatistics(
            data = response,
            statistics = statistics
        )
    }

    private fun MovieMongoModel.toMovieSummary(): MovieSummary =
        MovieSummary(
            id = 1L,    // TODO: Change String -> Long
            title = title,
            productionYear = productionYear,
            length = length,
            coverUrl = coverUrl,
            genres = genres.map {
                Genre(
                    id = 1L,     // TODO: Change String -> Long
                    name = it.name
                )
            },
        )

    override fun get(movieId: Long): ResponseWithStatistics<Movie> {
        TODO("Not yet implemented")
    }

    override fun add(request: AddMovieRequest): ResponseWithStatistics<Movie> {
        TODO("Not yet implemented")
    }

    override fun delete(movieId: Long): ResponseWithStatistics<Unit> {
        TODO("Not yet implemented")
    }
}