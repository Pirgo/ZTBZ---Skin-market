package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesResponse
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.databases.mondodb.models.MovieMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.MovieMongoRepository
import com.pk.zbtz.zbtzbackend.domain.*
import org.springframework.data.domain.Page
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
        val sortOrder = getSortingDirection(sortingOrder)
        val sortField = getSortFieldName(sort)
        val pageRequest = createPageRequest(offset ?: 0, pageSize ?: 10, sortOrder, sortField)

        val moviesPage = when (year) {
            null -> repository.findAllByFilters(titleToSearch.orEmpty(), platformName.orEmpty(), pageRequest)
            else -> repository.findAllByFilters(titleToSearch.orEmpty(), platformName.orEmpty(), year, pageRequest)
        }

        val movieSummaries = moviesPage.content.map { it.toMovieSummary() }
        val nextOffset = calculateNextOffset(moviesPage, offset, pageSize)
        val response = GetMoviesResponse(
            movies = movieSummaries,
            nextOffset = nextOffset,
            totalPages = moviesPage.totalPages,
            totalRecords = moviesPage.totalElements.toInt()
        )

        val statistics = Statistics()       // TODO: Calculate statistics

        return ResponseWithStatistics(
            data = response,
            statistics = statistics
        )
    }

    private fun getSortingDirection(sortingOrder: GetMoviesSortingOrder?): Sort.Direction =
        sortingOrder?.let {
            when (it) {
                GetMoviesSortingOrder.ASC -> Sort.Direction.ASC
                GetMoviesSortingOrder.DESC -> Sort.Direction.DESC
            }
        } ?: Sort.Direction.ASC

    private fun getSortFieldName(sort: GetMoviesSorting?): String =
        sort?.let {
            when (it) {
                GetMoviesSorting.ID -> "id"
                GetMoviesSorting.TITLE -> "title"
                GetMoviesSorting.PRODUCTION_YEAR -> "productionYear"
                GetMoviesSorting.RATING -> "rating"
                GetMoviesSorting.PLATFORM -> "platforms.name"
                GetMoviesSorting.GENRE -> "genres.name"
            }
        } ?: "id"

    private fun createPageRequest(
        offset: Int,
        pageSize: Int,
        sortOrder: Sort.Direction,
        sortField: String
    ): PageRequest = PageRequest.of(offset, pageSize, Sort.by(sortOrder, sortField))

    private fun MovieMongoModel.toMovieSummary(): MovieSummary =
        MovieSummary(
            id = id,
            title = title,
            productionYear = productionYear,
            length = length,
            coverUrl = coverUrl,
            genres = genres.map {
                Genre(
                    id = it.id,
                    name = it.name
                )
            },
        )

    private fun calculateNextOffset(
        moviesPage: Page<MovieMongoModel>,
        offset: Int?,
        pageSize: Int?
    ): Int? = if (moviesPage.hasNext()) (offset ?: 0) + (pageSize ?: 10) else null

    override fun get(movieId: Long): ResponseWithStatistics<Movie> {
        val movie = repository
            .findById(movieId)!!
            .toMovie()

        return ResponseWithStatistics(
            data = movie,
            statistics = Statistics()
        )
    }

    fun MovieMongoModel.toMovie(): Movie =
        Movie(
            id = this.id,
            title = this.title,
            platforms = this.platforms.map { Platform(it.id, it.name, it.logoUrl) },
            genres = this.genres.map { Genre(it.id, it.name) },
            productionYear = this.productionYear,
            rating = this.rating,
            plot = this.plot,
            coverUrl = this.coverUrl,
            budget = this.budget,
            length = this.length,
            actors = this.actors.map { Movie.MovieHuman.Actor(it.id, it.name, it.photoUrl, it.character) },
            directors = this.directors.map { Movie.MovieHuman.Director(it.id, it.name, it.photoUrl) }
        )

    override fun add(request: AddMovieRequest): ResponseWithStatistics<Movie> {
        TODO("Not yet implemented")
    }

    override fun delete(movieId: Long): ResponseWithStatistics<Unit> {
        TODO("Not yet implemented")
    }
}