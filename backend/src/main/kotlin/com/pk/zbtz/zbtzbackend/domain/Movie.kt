package com.pk.zbtz.zbtzbackend.domain

data class Movie(
    val id: String,
    val title: String,
    val platforms: List<Platform>,
    val genres: List<Genre>,
    val productionYear: Int,
    val rating: Float,
    val plot: String,
    val coverUrl: String,
    val budget: Float,
    val length: Float,
    val actors: List<MovieHuman.Actor>,
    val directors: List<MovieHuman.Director>,
) {
    sealed interface MovieHuman {
        val id: String
        val name: String
        val photoUrl: String

        data class Actor(
            override val id: String,
            override val name: String,
            override val photoUrl: String,
            val character: String,
        ): MovieHuman

        data class Director(
            override val id: String,
            override val name: String,
            override val photoUrl: String,
        ): MovieHuman
    }
}