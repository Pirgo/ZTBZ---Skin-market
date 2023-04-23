package com.pk.zbtz.zbtzbackend.databases.mondodb.services

import com.pk.zbtz.zbtzbackend.databases.mondodb.models.MovieMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.MovieMongoRepository
import io.github.serpro69.kfaker.Faker
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class DummyMongoDataService(
    private val movieRepository: MovieMongoRepository,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun generateDummyData() {
        val faker = Faker()
        val numberOfMovies = 10

        repeat(numberOfMovies) {
            val movie = generateFakeMovie(faker)

            movieRepository.save(movie)
        }
    }

    private fun generateFakeMovie(faker: Faker): MovieMongoModel {
        val platforms = (1..3).map {
            MovieMongoModel.PlatformMovieMongo(
                id = null,
                name = faker.app.name(),
                logoUrl = faker.randomUrl()
            )
        }

        val genres = (1..3).map {
            MovieMongoModel.GenreMovieMongo(
                id = null,
                name = faker.book.genre()
            )
        }

        val actors = (1..5).map {
            MovieMongoModel.HumanMovieMongo.Actor(
                id = null,
                name = faker.name.name(),
                photoUrl = faker.randomUrl(),
                character = faker.superhero.name()
            )
        }

        val directors = (1..2).map {
            MovieMongoModel.HumanMovieMongo.Director(
                id = null,
                name = faker.name.name(),
                photoUrl = faker.randomUrl()
            )
        }

        return MovieMongoModel(
            id = null,
            title = faker.movie.title(),
            platforms = platforms,
            genres = genres,
            productionYear = faker.random.nextInt(1970, 2023),
            rating = faker.random.nextFloat() * 2,
            plot = faker.lorem.words(),
            coverUrl = faker.randomUrl(),
            budget = faker.random.nextFloat() * 300_000_000,
            length = faker.random.nextInt(60, 180).toFloat(),
            actors = actors,
            directors = directors
        )
    }

    private fun Faker.randomUrl(): String =
        this.string.numerify("www.example####.com")
}