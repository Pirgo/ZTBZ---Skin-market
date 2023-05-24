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


/**
Class generates and populates MongoDB with dummy data
for Movie and Human entities upon application startup. It is useful for development
and testing purposes when a pre-populated database is required for demos or
validating application functionality.
 */
@Service
class DummyMongoDataService(
    private val movieRepository: MovieMongoRepository,
    private val humanMongoRepository: HumanMongoRepository,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun generateDummyData() {
        val faker = Faker()
        val numberOfMovies = 10000
        val numberOfHumans = 50000

        val people = (1..numberOfHumans)
            .map { generateFakeHuman(faker) }
            .map(humanMongoRepository::save)
            .toMutableList()

        repeat(numberOfMovies) {
            people.clear()
            people.addAll(humanMongoRepository.findAll().toList())

            val movie = generateFakeMovie(faker).let(movieRepository::save)

            val actors = people.shuffled().take(faker.random.nextInt(1, 20)).map { person ->
                person.copy(
                    functions = person.functions.copy(
                        actor = person.functions.actor + HumanMongoModel.FunctionsValueMongo.FunctionMongo.ActorMongo(
                            filmId = movie.id.orEmpty(),
                            title = movie.title
                        )
                    )
                ).let(humanMongoRepository::save)

                MovieMongoModel.HumanMovieMongo.Actor(
                    id = person.id,
                    name = "${person.firstName} ${person.secondName}",
                    photoUrl = person.photoUrl.orEmpty(),
                    character = faker.superhero.name()
                )
            }

            people.clear()
            people.addAll(humanMongoRepository.findAll().toList())

            val directors = people.shuffled().take(faker.random.nextInt(1, 20)).map { person ->
                person.copy(
                    functions = person.functions.copy(
                        director = person.functions.director + HumanMongoModel.FunctionsValueMongo.FunctionMongo.DirectorMongo(
                            filmId = movie.id.orEmpty(),
                            title = movie.title
                        )
                    )
                ).let(humanMongoRepository::save)

                MovieMongoModel.HumanMovieMongo.Director(
                    id = person.id,
                    name = "${person.firstName} ${person.secondName}",
                    photoUrl = person.photoUrl.orEmpty()
                )
            }

            movie.copy(
                actors = actors,
                directors = directors,
            ).let(movieRepository::save)
        }
    }


    private fun generateFakeMovie(faker: Faker): MovieMongoModel {
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
            title = faker.movie.title(),
            platforms = PLATFORMS.getRandomElements(faker.random.nextInt(3)),
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
        return HumanMongoModel(
            firstName = faker.name.firstName(),
            secondName = faker.name.lastName(),
            photoUrl = faker.randomUrl(),
            birthday = faker.birthDate(),
            placeOfBirth = faker.address.city(),
            deathDay = faker.birthDate().takeIf { faker.random.nextBoolean() },
            description = faker.lorem.words(),
            functions = HumanMongoModel.FunctionsValueMongo(
                director = emptyList(),
                actor = emptyList()
            )
        )
    }

    private fun Faker.randomUrl(): String =
        this.string.numerify("www.example####.com")

    private fun Faker.randomId(): String =
        this.random.nextLong(Long.MAX_VALUE).let(::abs).toString()

    private fun Faker.birthDate(): LocalDate =
        this.person.birthDate(age = this.random.nextLong(100))

    private fun <T> List<T>.getRandomElements(n: Int): List<T> =
        when {
            this.isEmpty() || n >= size -> this
            else -> (indices).shuffled().take(n).map { this[it] }
        }

    private companion object {
        val PLATFORMS = listOf(
            MovieMongoModel.PlatformMovieMongo(
                id = "1",
                name = "Netflix",
                logoUrl = "https://historia.org.pl/wp-content/uploads/2018/04/netflix-logo.jpg"
            ),
            MovieMongoModel.PlatformMovieMongo(
                id = "2",
                name = "Hulu",
                logoUrl = "https://i0.wp.com/cordcuttersnews.com/wp-content/uploads/2019/09/Hulu-New-Logo-Rec.png?w=1011&ssl=1"
            ),
            MovieMongoModel.PlatformMovieMongo(
                id = "3",
                name = "Amazon Prime",
                logoUrl = "https://m.media-amazon.com/images/G/01/primevideo/seo/primevideo-seo-logo.png"
            ),
        )
    }
}