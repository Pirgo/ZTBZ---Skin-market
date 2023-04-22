package com.pk.zbtz.zbtzbackend.controllers.movie.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesResponse
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSorting
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.GetMoviesSortingOrder
import com.pk.zbtz.zbtzbackend.domain.*
import org.springframework.stereotype.Service
import kotlin.math.ceil

/*
* Only for testing purpose. Functions return dummy data to imitate API usage.
*/
@Service
class FakeMovieService : MovieService {
    private val movies: MutableList<Movie> = dummyMovies.toMutableList()

    override fun getAll(
        sort: GetMoviesSorting?,
        sortingOrder: GetMoviesSortingOrder?,
        titleToSearch: String?,
        platformName: String?,
        year: Int?,
        pageSize: Int?,
        offset: Int?
    ): ResponseWithStatistics<GetMoviesResponse> {
        var filteredMovies = movies.toList()

        // Apply filters
        titleToSearch?.let { search ->
            filteredMovies = filteredMovies.filter { movie -> movie.title.contains(search, ignoreCase = true) }
        }
        platformName?.let { platform ->
            filteredMovies = filteredMovies.filter { movie -> movie.platforms.any { it.name == platform } }
        }
        year?.let { productionYear ->
            filteredMovies = filteredMovies.filter { movie -> movie.productionYear == productionYear }
        }

        val sortedMovies = filteredMovies.sortedWith { m1: Movie, m2: Movie ->
            when (sort) {
                GetMoviesSorting.ID -> m1.id.compareTo(m2.id)
                GetMoviesSorting.TITLE -> m1.title.compareTo(m2.title)
                GetMoviesSorting.PRODUCTION_YEAR -> m1.productionYear.compareTo(m2.productionYear)
                GetMoviesSorting.RATING -> m1.rating.compareTo(m2.rating)
                GetMoviesSorting.PLATFORM -> m1.platforms.first().name.compareTo(m2.platforms.first().name)
                GetMoviesSorting.GENRE -> m1.genres.first().name.compareTo(m2.genres.first().name)
                null -> 0
            }
        }

        // Apply sorting order
        val orderedMovies = when (sortingOrder) {
            GetMoviesSortingOrder.ASC -> sortedMovies
            GetMoviesSortingOrder.DESC -> sortedMovies.reversed()
            null -> sortedMovies
        }

        // Apply pagination
        val offsetValue = offset ?: 0
        val pageSizeValue = pageSize ?: 10
        val paginatedMovies = orderedMovies.drop(offsetValue).take(pageSizeValue)
        val movieSummaries = paginatedMovies.map { movie ->
            MovieSummary(
                id = movie.id,
                title = movie.title,
                productionYear = movie.productionYear,
                length = movie.length,
                coverUrl = movie.coverUrl,
                genres = movie.genres
            )
        }

        val response = GetMoviesResponse(
            movies = movieSummaries,
            nextOffset = if (offsetValue + pageSizeValue < orderedMovies.size) offsetValue + pageSizeValue else null,
            totalPages = ceil(orderedMovies.size.toFloat() / pageSizeValue).toInt(),
            totalRecords = orderedMovies.size
        )

        return ResponseWithStatistics(
            data = response,
            statistics = dummyStatistics
        )
    }

    private fun movieToMovieSummary(movie: Movie): MovieSummary =
        MovieSummary(
            id = movie.id,
            title = movie.title,
            productionYear = movie.productionYear,
            length = movie.length,
            coverUrl = movie.coverUrl,
            genres = movie.genres
        )

    override fun get(movieId: Long): ResponseWithStatistics<Movie> =
        ResponseWithStatistics(
            data = movies.find { it.id == movieId },
            statistics = dummyStatistics,
        )

    override fun add(request: AddMovieRequest): ResponseWithStatistics<Movie> {
        val platforms = request.platformIds.map { platformId ->
            Platform(
                id = platformId,
                name = "Platform $platformId",
                logoUrl = "https://example.com/platform$platformId/logo.jpg"
            )
        }

        val genres = request.genreIds.map { genreId ->
            Genre(
                id = genreId,
                name = "Genre $genreId",
            )
        }

        val actors = request.actors.map { actor ->
            Movie.MovieHuman.Actor(
                id = actor.id,
                name = "Actor ${actor.id}",
                photoUrl = "https://example.com/actor${actor.id}/photo.jpg",
                character = actor.character
            )
        }

        val directors = request.directors.map { director ->
            Movie.MovieHuman.Director(
                id = director.id,
                name = "Director ${director.id}",
                photoUrl = "https://example.com/director${director.id}/photo.jpg"
            )
        }

        val newMovie = Movie(
            id = request.id,
            title = request.title,
            platforms = platforms,
            genres = genres,
            productionYear = request.productionYear,
            rating = request.rating ?: 0.0f,
            plot = request.plot,
            coverUrl = request.coverUrl,
            budget = request.budget.toFloat(),
            length = 0.0f, // You need to provide a length value for the new movie
            actors = actors,
            directors = directors
        )
        movies.add(newMovie)
        return ResponseWithStatistics(
            data = newMovie,
            statistics = dummyStatistics // Implement statistics if needed
        )
    }

    override fun delete(movieId: Long): ResponseWithStatistics<Unit> {
        val movieToRemove = movies.find { it.id == movieId }
        movies.remove(movieToRemove)

        return ResponseWithStatistics(
            statistics = dummyStatistics,
        )
    }

    private companion object {
        val dummyMovies = listOf(
            Movie(
                id = 1L,
                title = "Example Movie 1",
                platforms = listOf(Platform(1L, "Platform 1", "https://example.com/platform1/logo.jpg")),
                genres = listOf(Genre(1L, "Action"), Genre(2L, "Comedy")),
                productionYear = 2020,
                rating = 7.5f,
                plot = "A thrilling action-comedy about unlikely heroes saving the world.",
                coverUrl = "https://example.com/movie1/cover.jpg",
                budget = 1000000.0f,
                length = 120.0f,
                actors = listOf(
                    Movie.MovieHuman.Actor(
                        id = 1L,
                        name = "Actor 1",
                        photoUrl = "https://example.com/actor1/photo.jpg",
                        character = "Hero 1"
                    )
                ),
                directors = listOf(
                    Movie.MovieHuman.Director(
                        id = 1L,
                        name = "Director 1",
                        photoUrl = "https://example.com/director1/photo.jpg"
                    )
                )
            ),
            Movie(
                id = 2L,
                title = "Example Movie 2",
                platforms = listOf(Platform(2L, "Platform 2", "https://example.com/platform2/logo.jpg")),
                genres = listOf(Genre(3L, "Drama"), Genre(4L, "Romance")),
                productionYear = 2019,
                rating = 8.0f,
                plot = "A heartwarming drama about love and loss.",
                coverUrl = "https://example.com/movie2/cover.jpg",
                budget = 1500000.0f,
                length = 110.0f,
                actors = listOf(
                    Movie.MovieHuman.Actor(
                        id = 2L,
                        name = "Actor 2",
                        photoUrl = "https://example.com/actor2/photo.jpg",
                        character = "Main character"
                    )
                ),
                directors = listOf(
                    Movie.MovieHuman.Director(
                        id = 2L,
                        name = "Director 2",
                        photoUrl = "https://example.com/director2/photo.jpg"
                    )
                )
            ),
            Movie(
                id = 3L,
                title = "Example Movie 3",
                platforms = listOf(Platform(3L, "Platform 3", "https://example.com/platform3/logo.jpg")),
                genres = listOf(Genre(5L, "Science Fiction"), Genre(6L, "Thriller")),
                productionYear = 2021,
                rating = 8.5f,
                plot = "A futuristic sci-fi thriller about the dangers of advanced technology.",
                coverUrl = "https://example.com/movie3/cover.jpg",
                budget = 2000000.0f,
                length = 130.0f,
                actors = listOf(
                    Movie.MovieHuman.Actor(
                        id = 3L,
                        name = "Actor 3",
                        photoUrl = "https://example.com/actor3/photo.jpg",
                        character = "Protagonist"
                    )
                ),
                directors = listOf(
                    Movie.MovieHuman.Director(
                        id = 3L,
                        name = "Director 3",
                        photoUrl = "https://example.com/director3/photo.jpg"
                    )
                )
            ),
            Movie(
                id = 4L,
                title = "Example Movie 4",
                platforms = listOf(Platform(4L, "Platform 4", "https://example.com/platform4/logo.jpg")),
                genres = listOf(Genre(7L, "Adventure"), Genre(8L, "Family")),
                productionYear = 2018,
                rating = 7.0f,
                plot = "A family-friendly adventure about a group of friends discovering a hidden world.",
                coverUrl = "https://example.com/movie4/cover.jpg",
                budget = 1200000.0f,
                length = 100.0f,
                actors = listOf(
                    Movie.MovieHuman.Actor(
                        id = 4L,
                        name = "Actor 4",
                        photoUrl = "https://example.com/actor4/photo.jpg",
                        character = "Explorer"
                    )
                ),
                directors = listOf(
                    Movie.MovieHuman.Director(
                        id = 4L,
                        name = "Director 4",
                        photoUrl = "https://example.com/director4/photo.jpg"
                    )
                )
            ),
            Movie(
                id = 5L,
                title = "Example Movie 5",
                platforms = listOf(Platform(5L, "Platform 5", "https://example.com/platform5/logo.jpg")),
                genres = listOf(Genre(9L, "Horror"), Genre(10L, "Mystery")),
                productionYear = 2022,
                rating = 6.5f,
                plot = "A chilling horror mystery where a group of friends must uncover the truth behind a terrifying secret.",
                coverUrl = "https://example.com/movie5/cover.jpg",
                budget = 800000.0f,
                length = 90.0f,
                actors = listOf(
                    Movie.MovieHuman.Actor(
                        id = 5L,
                        name = "Actor 5",
                        photoUrl = "https://example.com/actor5/photo.jpg",
                        character = "Survivor"
                    )
                ),
                directors = listOf(
                    Movie.MovieHuman.Director(
                        id = 5L,
                        name = "Director 5",
                        photoUrl = "https://example.com/director5/photo.jpg"
                    )
                )
            )
        )

        val dummyStatistics = Statistics(
            accessTime = 1000,
            databaseMemorySize = 2137,
        )
    }
}