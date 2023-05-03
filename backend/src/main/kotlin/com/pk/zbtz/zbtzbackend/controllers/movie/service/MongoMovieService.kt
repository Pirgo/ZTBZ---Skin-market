package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesResponse
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.databases.mondodb.models.MovieMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.MovieMongoRepository
import com.pk.zbtz.zbtzbackend.domain.*
import com.pk.zbtz.zbtzbackend.utils.ExecutionTimer
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class MongoMovieService(
    private val repository: MovieMongoRepository,
    private val executionTimer: ExecutionTimer,
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
            id = id.orEmpty(),
            title = title,
            productionYear = productionYear,
            length = length,
            coverUrl = coverUrl,
            genres = genres.map {
                Genre(
                    id = it.id.orEmpty(),
                    name = it.name
                )
            },
        )

    private fun calculateNextOffset(
        moviesPage: Page<MovieMongoModel>,
        offset: Int?,
        pageSize: Int?
    ): Int? = if (moviesPage.hasNext()) (offset ?: 0) + (pageSize ?: 10) else null

    @OptIn(ExperimentalStdlibApi::class)
    override fun get(movieId: String): ResponseWithStatistics<Movie> {
        val movie = repository
            .findById(movieId)
            .getOrNull()
            ?.toMovie()

        return ResponseWithStatistics(
            data = movie,
            statistics = Statistics()
        )
    }

    private fun MovieMongoModel.toMovie(): Movie =
        Movie(
            id = this.id.orEmpty(),
            title = this.title,
            platforms = this.platforms.map { Platform(it.id.orEmpty(), it.name, it.logoUrl) },
            genres = this.genres.map { Genre(it.id.orEmpty(), it.name) },
            productionYear = this.productionYear,
            rating = this.rating,
            plot = this.plot,
            coverUrl = this.coverUrl,
            budget = this.budget,
            length = this.length,
            actors = this.actors.map { Movie.MovieHuman.Actor(it.id.orEmpty(), it.name, it.photoUrl, it.character) },
            directors = this.directors.map { Movie.MovieHuman.Director(it.id.orEmpty(), it.name, it.photoUrl) }
        )

    override fun add(request: AddMovieRequest): ResponseWithStatistics<Movie> {
        val platforms = getAllPlatforms(request)
        val genres = getAllGenres(request)

        val movie = request
            .toMovieMongoModel(
                platforms = platforms,
                genres = genres,
                movieActors = emptyList(),      // TODO: Implement when PeopleRepository will be implemented
                movieDirectors = emptyList(),       // TODO: Implement when PeopleRepository will be implemented
            )
            .let(repository::save)
            .toMovie()

        return ResponseWithStatistics(
            data = movie,
            statistics = Statistics(),
        )
    }

    private fun getAllPlatforms(request: AddMovieRequest): List<MovieMongoModel.PlatformMovieMongo> =
        repository
            .findAllByPlatformIds(platformIds = request.platformIds)
            .flatMap(MovieMongoModel::platforms)
            .distinct()
            .filter { request.platformIds.contains(it.id) }

    private fun getAllGenres(request: AddMovieRequest): List<MovieMongoModel.GenreMovieMongo> =
        repository
            .findAllByGenreIds(genreId = request.genreIds)
            .flatMap(MovieMongoModel::genres)
            .distinct()
            .filter { request.genreIds.contains(it.id) }

    private fun AddMovieRequest.toMovieMongoModel(
        platforms: List<MovieMongoModel.PlatformMovieMongo>,
        genres: List<MovieMongoModel.GenreMovieMongo>,
        movieActors: List<MovieMongoModel.HumanMovieMongo.Actor>,
        movieDirectors: List<MovieMongoModel.HumanMovieMongo.Director>,
    ): MovieMongoModel =
        MovieMongoModel(
            title = title,
            platforms = platforms,
            genres = genres,
            productionYear = productionYear,
            rating = rating ?: 0f,
            plot = plot,
            coverUrl = coverUrl,
            budget = budget,
            length = length,
            actors = movieActors,
            directors = movieDirectors,
        )

    override fun delete(movieId: String): ResponseWithStatistics<Unit> {
        repository.deleteById(movieId)

        return ResponseWithStatistics(
            statistics = Statistics()
        )
    }
}