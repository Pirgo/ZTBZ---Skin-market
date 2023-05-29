package com.pk.zbtz.zbtzbackend.databases.mondodb.services

import com.pk.zbtz.zbtzbackend.databases.mondodb.models.HumanMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.models.MovieMongoModel
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.HumanMongoRepository
import com.pk.zbtz.zbtzbackend.databases.mondodb.repositories.MovieMongoRepository
import com.pk.zbtz.zbtzbackend.fakes.generators.HumanPhotoUrlGenerator
import com.pk.zbtz.zbtzbackend.fakes.generators.MovieCoversUrlGenerator
import io.github.serpro69.kfaker.Faker
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.math.abs


/**
Class generates and populates MongoDB with dummy data
for Movie and Human entities upon application startup with `generateMongoData` flag.
It is useful for development and testing purposes when a pre-populated database is required for demos or
validating application functionality.
 */
@Service
class DummyMongoDataService(
    private val movieRepository: MovieMongoRepository,
    private val humanRepository: HumanMongoRepository,
    private val movieCoversUrlGenerator: MovieCoversUrlGenerator,
    private val humanPhotoUrlGenerator: HumanPhotoUrlGenerator,
) {
    fun clearAndGenerateDummyData() {
        movieRepository.deleteAll()
        humanRepository.deleteAll()
        generateDummyData()
    }

    private fun generateDummyData() {
        val faker = Faker()
        val numberOfMovies = 70000
        val numberOfHumans = 150000
        val maxNumberOfActorsForSingleFilm = 50
        val maxNumberOfDirectorsForSingleFilm = 50

        println("GENERATING PEOPLE")
        val people: MutableList<HumanMongoModel> = (1..numberOfHumans)
            .map { generateFakeHuman(faker) }
            .let(humanRepository::saveAll)

        println("GENERATING MOVIES")
        val movies: MutableList<MovieMongoModel> = (1..numberOfMovies)
            .map { generateFakeMovie(faker) }
            .let(movieRepository::saveAll)

        generateAndAddActorsAndDirectors(
            movies = movies,
            maxNumberOfActorsForSingleFilm = maxNumberOfActorsForSingleFilm,
            faker = faker,
            numberOfHumans = numberOfHumans,
            people = people,
            maxNumberOfDirectorsForSingleFilm = maxNumberOfDirectorsForSingleFilm
        )
    }

    private fun generateAndAddActorsAndDirectors(
        movies: MutableList<MovieMongoModel>,
        maxNumberOfActorsForSingleFilm: Int,
        faker: Faker,
        numberOfHumans: Int,
        people: MutableList<HumanMongoModel>,
        maxNumberOfDirectorsForSingleFilm: Int
    ) {
        println("GENERATING ACTORS AND DIRECTORS")
        movies.mapIndexed { movieIndex, movie ->
            println("Generating actors and directors for movie with index: $movieIndex")

            val randomActorsIndexes = generateRandomHumanIndexes(
                maxNumberOfIndexes = maxNumberOfActorsForSingleFilm,
                faker = faker,
                numberOfHumans = numberOfHumans,
            )
            updatePeopleWithActorRole(
                randomActorsIndexes = randomActorsIndexes,
                people = people,
                movie = movie,
            )
            val movieActors = makeMovieActors(
                randomActorsIndexes = randomActorsIndexes,
                people = people,
                faker = faker,
            )

            val randomDirectorsIndexes = generateRandomHumanIndexes(
                maxNumberOfIndexes = maxNumberOfDirectorsForSingleFilm,
                faker = faker,
                numberOfHumans = numberOfHumans,
            )
            updatePeopleWithDirectorRole(
                randomDirectorsIndexes = randomDirectorsIndexes,
                people = people,
                movie = movie,
            )
            val movieDirectors = makeMovieDirectors(
                randomDirectorsIndexes = randomDirectorsIndexes,
                people = people,
            )

            movie.copy(
                actors = movieActors,
                directors = movieDirectors,
            )
        }.let(movieRepository::saveAll)

        people.let(humanRepository::saveAll)
        println("FINISHED GENERATING !!!")
    }

    private fun generateFakeMovie(faker: Faker): MovieMongoModel {
        val genres = (1..3).map {
            MovieMongoModel.GenreMovieMongo(
                id = faker.randomId(),
                name = faker.book.genre()
            )
        }

        return MovieMongoModel(
            title = faker.movie.title(),
            platforms = platforms.getRandomElements(faker.random.nextInt(platforms.size)),
            genres = genres,
            productionYear = faker.random.nextInt(1970, 2023),
            rating = faker.random.nextFloat() * 2,
            plot = faker.lorem.words(),
            coverUrl = movieCoversUrlGenerator.generate(),
            budget = faker.random.nextFloat() * 300_000_000,
            length = faker.random.nextInt(60, 180).toFloat(),
            actors = emptyList(),
            directors = emptyList(),
        )
    }


    private fun generateFakeHuman(faker: Faker): HumanMongoModel =
        HumanMongoModel(
            firstName = faker.name.firstName(),
            secondName = faker.name.lastName(),
            photoUrl = humanPhotoUrlGenerator.generate(),
            birthday = faker.birthDate(),
            placeOfBirth = faker.address.city(),
            deathDay = faker.birthDate().takeIf { faker.random.nextBoolean() },
            description = faker.lorem.words(),
            functions = HumanMongoModel.FunctionsValueMongo(
                director = emptyList(),
                actor = emptyList()
            )
        )

    private fun generateRandomHumanIndexes(
        maxNumberOfIndexes: Int,
        faker: Faker,
        numberOfHumans: Int
    ): List<Int> = (1..maxNumberOfIndexes).map { faker.random.nextInt(0, numberOfHumans - 1) }.distinct()

    private fun updatePeopleWithActorRole(
        randomActorsIndexes: List<Int>,
        people: MutableList<HumanMongoModel>,
        movie: MovieMongoModel
    ) {
        randomActorsIndexes.forEach { index ->
            people[index] = people[index]
                .let { person ->
                    person.copy(
                        functions = person.functions.copy(
                            actor = person.functions.actor + HumanMongoModel.FunctionsValueMongo.FunctionMongo.ActorMongo(
                                filmId = movie.id.orEmpty(),
                                title = movie.title
                            )
                        )
                    )
                }
        }
    }

    private fun makeMovieActors(
        randomActorsIndexes: List<Int>,
        people: MutableList<HumanMongoModel>,
        faker: Faker
    ) = randomActorsIndexes
        .map(people::get)
        .map { person ->
            MovieMongoModel.HumanMovieMongo.Actor(
                id = person.id,
                name = "${person.firstName} ${person.secondName}",
                photoUrl = person.photoUrl.orEmpty(),
                character = faker.superhero.name()
            )
        }

    private fun updatePeopleWithDirectorRole(
        randomDirectorsIndexes: List<Int>,
        people: MutableList<HumanMongoModel>,
        movie: MovieMongoModel
    ) {
        randomDirectorsIndexes.forEach { index ->
            people[index] = people[index]
                .let { person ->
                    person.copy(
                        functions = person.functions.copy(
                            director = person.functions.director + HumanMongoModel.FunctionsValueMongo.FunctionMongo.DirectorMongo(
                                filmId = movie.id.orEmpty(),
                                title = movie.title
                            )
                        )
                    )
                }
        }
    }

    private fun makeMovieDirectors(
        randomDirectorsIndexes: List<Int>,
        people: MutableList<HumanMongoModel>
    ) = randomDirectorsIndexes
        .map(people::get)
        .map { person ->
            MovieMongoModel.HumanMovieMongo.Director(
                id = person.id,
                name = "${person.firstName} ${person.secondName}",
                photoUrl = person.photoUrl.orEmpty()
            )
        }

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
        val platforms = listOf(
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