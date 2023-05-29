package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models

import com.pk.zbtz.zbtzbackend.NoArg
import com.pk.zbtz.zbtzbackend.databases.postgres.models.MoviePostgresModel
import jakarta.persistence.*

@NoArg
@Entity
class DirectorPostgresModel(
    @EmbeddedId
    val id: HumanMovieId,
    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movieId", referencedColumnName = "id")
    val movie: MoviePostgresModel,

    @ManyToOne
    @MapsId("humanId")
    @JoinColumn(name = "humanId", referencedColumnName = "id")
    val human: HumanPostgresModel
)