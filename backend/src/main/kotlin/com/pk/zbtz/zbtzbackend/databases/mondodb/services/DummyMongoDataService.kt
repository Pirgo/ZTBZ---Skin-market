package com.pk.zbtz.zbtzbackend.databases.mondodb.services

import com.pk.zbtz.zbtzbackend.databases.mondodb.models.HumanMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.models.MovieMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.HumanMongoRepository
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.MovieMongoRepository
import io.github.serpro69.kfaker.Faker
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.math.abs

@Service
class DummyMongoDataService(
    private val movieRepository: MovieMongoRepository,
    private val humanMongoRepository: HumanMongoRepository,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun generateDummyData() {
        val faker = Faker()
        val numberOfMovies = 10
        val numberOfHumans = 20

        repeat(numberOfMovies) {
            generateFakeMovie(faker).let(movieRepository::save)
        }

        repeat(numberOfHumans) {
            generateFakeHuman(faker).let(humanMongoRepository::save)
        }
    }

    private fun generateFakeMovie(faker: Faker): MovieMongoModel {
        val platforms = (1..3).map {
            MovieMongoModel.PlatformMovieMongo(
                id = faker.randomId(),
                name = faker.app.name(),
                logoUrl = faker.randomUrl()
            )
        }

        val genres = (1..3).map {
            MovieMongoModel.GenreMovieMongo(
                id = faker.randomId(),
                name = faker.book.genre()
            )
        }

        val actors = (1..5).map {
            MovieMongoModel.HumanMovieMongo.Actor(
                id = faker.randomId(),
                name = faker.name.name(),
                photoUrl = faker.randomUrl(),
                character = faker.superhero.name()
            )
        }

        val directors = (1..2).map {
            MovieMongoModel.HumanMovieMongo.Director(
                id = faker.randomId(),
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
            directors = directors,
        )
    }

    private fun generateFakeHuman(faker: Faker): HumanMongoModel {
        val directorFunctions = (1..3).map {
            HumanMongoModel.FunctionsValueMongo.FunctionMongo.DirectorMongo(
                filmId = faker.randomId(),
                title = faker.movie.title()
            )
        }

        val actorFunctions = (1..3).map {
            HumanMongoModel.FunctionsValueMongo.FunctionMongo.ActorMongo(
                filmId = faker.randomId(),
                title = faker.movie.title()
            )
        }

        val functionsValue = HumanMongoModel.FunctionsValueMongo(
            director = directorFunctions,
            actor = actorFunctions
        )

        return HumanMongoModel(
            id = null,
            firstName = faker.name.firstName(),
            secondName = faker.name.lastName(),
            photoUrl = faker.randomUrl(),
            birthday = faker.birthDate(),
            placeOfBirth = faker.address.city(),
            deathDay = faker.birthDate().takeIf { faker.random.nextBoolean() },
            description = faker.lorem.words(),
            functions = functionsValue
        )
    }

    private fun Faker.randomUrl(): String =
        this.string.numerify("www.example####.com")

    private fun Faker.randomId(): String =
        this.random.nextLong(Long.MAX_VALUE).let(::abs).toString()

    private fun Faker.birthDate(): LocalDate =
        this.person.birthDate(age = this.random.nextLong(100))
}