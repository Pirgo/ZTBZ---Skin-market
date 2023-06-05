package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.services

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.ActorPostgresModel
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.DirectorPostgresModel
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.HumanMovieId
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories.GenresPostgresRepository
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories.PlatformsPostgresRepository
import com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses.AddMovieRequest
import com.pk.zbtz.zbtzbackend.databases.postgres.models.MoviePostgresModel
import com.pk.zbtz.zbtzbackend.databases.postgres.repositories.MoviePostgresRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MovieService(
    private val movieRepository: MoviePostgresRepository,
    private val platformsPostgresRepository: PlatformsPostgresRepository,
    private val genresPostgresRepository: GenresPostgresRepository,
    private val humanService: HumanService,
    private val actorService: ActorService,
    private val directorService: DirectorService
) {
    fun getById(id: Int): MoviePostgresModel? {
        return movieRepository.findById(id).orElseThrow()
    }

    fun getAll(offset: Int, limit: Int): List<MoviePostgresModel> {
        return movieRepository.findAllWithOffsetAndLimit(offset, limit)
    }

    fun getAllByYear(year: Int, offset: Int, limit: Int): List<MoviePostgresModel> {
        return movieRepository.findAllByYearWithOffsetAndLimit(year, offset, limit)
    }

    fun getAllByPlatform(platform: String, offset: Int, limit: Int): List<MoviePostgresModel> {
        return movieRepository.findAllByPlatformWithOffsetAndLimit(platform, offset, limit)
    }

    fun getAllByTitle(title: String, offset: Int, limit: Int): List<MoviePostgresModel> {
        return movieRepository.findAllByTitleWithOffsetAndLimit("%$title%", offset, limit)
    }

    fun getAllByTitleAndPlatform(title: String, platform: String, offset: Int, limit: Int): List<MoviePostgresModel> {
        return movieRepository.findAllByTitleAndPlatformWithOffsetAndLimit("%$title%", platform, offset, limit)
    }

    fun getAllByTitleAndYear(title: String, year: Int, offset: Int, limit: Int): List<MoviePostgresModel> {
        return movieRepository.findAllByTitleAndYearWithOffsetAndLimit("%$title%", year, offset, limit)
    }

    fun getAllByPlatformAndYear(platform: String, year: Int, offset: Int, limit: Int): List<MoviePostgresModel> {
        return movieRepository.findAllByPlatformAndYearWithOffsetAndLimit(platform, year, offset, limit)
    }

    fun getAllByPlatformAndTitleAndYear(platform: String, title: String, year: Int, offset: Int, limit: Int): List<MoviePostgresModel> {
        return movieRepository.findAllByPlatformAndYearAndTitleWithOffsetAndLimit(platform, year, "%$title%", offset, limit)
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
        //it would be better to generete id manually so there would be only one instert via movieRepository.save
        //it works but not quite fast as it can be
        var savedMovie =  movieRepository.save(movie)
        val actors = request.actors.map { ActorPostgresModel(HumanMovieId(it.id.toInt(), savedMovie.id!!), it.character, savedMovie, humanService.getById(it.id.toInt())!! ) }
        val directors = request.directors.map { DirectorPostgresModel(HumanMovieId(it.id.toInt(), savedMovie.id!!), savedMovie, humanService.getById(it.id.toInt())!! ) }
        actors.map { actorService.save(it) }
        directors.map { directorService.save(it) }
        return MoviePostgresModel(
                title = request.title,
                platforms = platforms.toList(),
                productionYear = request.productionYear,
                genres = genres.toList(),
                rating = request.rating,
                plot = request.plot,
                coverUrl = request.coverUrl,
                budget = request.budget,
                length = request.length,
                actors = actors,
                directors = directors,
                id = savedMovie.id
            )

    }

    fun delete(id: Int) {
        movieRepository.deleteById(id)
    }


}