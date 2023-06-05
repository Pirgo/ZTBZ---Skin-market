package com.pk.zbtz.zbtzbackend.databases.postgres.models

import com.pk.zbtz.zbtzbackend.NoArg
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.ActorPostgresModel
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.DirectorPostgresModel
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.GenrePostgresModel
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.PlatformPostgresModel
import com.pk.zbtz.zbtzbackend.domain.Genre
import com.pk.zbtz.zbtzbackend.domain.Movie
import com.pk.zbtz.zbtzbackend.domain.MovieSummary
import jakarta.persistence.*

@NoArg
@Table(name = "movies", indexes = [Index(columnList = "rating")])
@Entity
class MoviePostgresModel(
    val title: String,
    @ManyToMany
    @JoinTable(
        name = "movie_platforms",
        joinColumns = [JoinColumn(name = "movie_id")],
        inverseJoinColumns = [JoinColumn(name = "platform_id")]
    )
    val platforms: List<PlatformPostgresModel>,
    @ManyToMany
    @JoinTable(
        name = "movie_genres",
        joinColumns = [JoinColumn(name = "movie_id")],
        inverseJoinColumns = [JoinColumn(name = "genre_id")]
    )
    val genres: List<GenrePostgresModel>,
    val productionYear: Int,
    val rating: Float,
    val plot: String,
    val coverUrl: String,
    val budget: Float,
    val length: Float,
    @OneToMany(mappedBy = "movie", cascade = [CascadeType.ALL], orphanRemoval = true)
    var actors: List<ActorPostgresModel>,
    @OneToMany(mappedBy = "movie", cascade = [CascadeType.ALL], orphanRemoval = true)
    var directors: List<DirectorPostgresModel>,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
) {
    fun toMovie(): Movie {
        return Movie(
            id = id.toString(),
            length = length,
            budget = budget,
            coverUrl = coverUrl,
            plot = plot,
            platforms = platforms.map { it.toPlatfrom() },
            title = title,
            productionYear = productionYear,
            rating = rating,
            genres = genres.map { it.toGenre() },
            actors = actors.map { Movie.MovieHuman.Actor(it.human.id.toString(), it.human.firstName + " " + it.human.secondName, it.human.photoUrl, it.character) },
            directors = directors.map { Movie.MovieHuman.Director(it.human.id.toString(), it.human.firstName + " " + it.human.secondName, it.human.photoUrl) }
        )
    }

    fun toMovieSummary(): MovieSummary {
        return MovieSummary(
            id = id.toString(),
            title = title,
            productionYear = productionYear,
            length = length,
            coverUrl = coverUrl,
            genres = genres.map {
                Genre(
                    id = it.id.toString(),
                    name = it.name
                )
            }
        )
    }
}