package com.pk.zbtz.zbtzbackend.databases.mondodb.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "movies")
data class MovieMongoModel(
    @Id
    val id: String? = null,
    val title: String,
    val platforms: List<PlatformMovieMongo>,
    val genres: List<GenreMovieMongo>,
    val productionYear: Int,
    val rating: Float,
    val plot: String,
    val coverUrl: String,
    val budget: Float,
    val length: Float,
    val actors: List<HumanMovieMongo.Actor>,
    val directors: List<HumanMovieMongo.Director>,
) {
    sealed interface HumanMovieMongo {
        val id: String? // Change to String? as MongoDB uses ObjectId
        val name: String
        val photoUrl: String

        data class Actor(
            override val id: String? = null, // Change to String? as MongoDB uses ObjectId
            override val name: String,
            override val photoUrl: String,
            val character: String,
        ) : HumanMovieMongo

        data class Director(
            override val id: String? = null, // Change to String? as MongoDB uses ObjectId
            override val name: String,
            override val photoUrl: String,
        ) : HumanMovieMongo
    }

    data class PlatformMovieMongo(
        val id: String?,
        val name: String,
        val logoUrl: String,
    )

    data class GenreMovieMongo(
        val id: String?,
        val name: String,
    )
}