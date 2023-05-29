package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.services

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories.GenresPostgresRepository
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories.PlatformsPostgresRepository
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.databases.postgres.models.MoviePostgresModel
import com.pk.zbtz.zbtzbackend.databases.postgres.repositories.MoviePostgresRepository
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieRepository: MoviePostgresRepository,
    private val platformsPostgresRepository: PlatformsPostgresRepository,
    private val genresPostgresRepository: GenresPostgresRepository
) {
    fun getById(id: Int): MoviePostgresModel? {
        return movieRepository.findById(id).orElseThrow()
    }

    fun save(request: AddMovieRequest): MoviePostgresModel {
        val platforms = platformsPostgresRepository.findAllById(request.platformIds.map { it.toInt() })
        val genres = genresPostgresRepository.findAllById(request.genreIds.map{ it.toInt()})
        val movie = MoviePostgresModel(
            title = request.title,
            platforms = platforms.toList(),
            productionYear = request.productionYear,
            genres = genres.toList(),
            rating = request.rating,
            plot = request.plot,
            coverUrl = request.coverUrl,
            budget = request.budget,
            length = request.length,
            actors = emptyList(),
            directors = emptyList(),
            id = null
        )
        return movieRepository.save(movie)
    }

    fun delete(id: Int) {
        movieRepository.deleteById(id)
    }


}