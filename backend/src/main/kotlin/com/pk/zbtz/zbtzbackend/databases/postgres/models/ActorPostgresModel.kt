package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models

import com.pk.zbtz.zbtzbackend.NoArg
import com.pk.zbtz.zbtzbackend.databases.postgres.models.MoviePostgresModel
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId

@NoArg
@Entity
class ActorPostgresModel(
    @EmbeddedId
    val id: HumanMovieId,
    val character: String,

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movieId", referencedColumnName = "id")
    val movie: MoviePostgresModel,

    @ManyToOne
    @MapsId("humanId")
    @JoinColumn(name = "humanId", referencedColumnName = "id")
    val human: HumanPostgresModel
)