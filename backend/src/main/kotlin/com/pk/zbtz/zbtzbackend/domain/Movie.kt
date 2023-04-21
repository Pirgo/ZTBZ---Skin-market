package com.pk.zbtz.zbtzbackend.domain

data class Movie(
    val id: Long,
    val title: String,
    val platforms: List<Platform>,
    val genres: List<Genre>,
    val productionYear: Int,
    val rating: Float?,
    val plot: String,
    val coverUrl: String,
    val budget: Long,
    val actors: List<MovieHuman.Actor>,
    val directors: List<MovieHuman.Director>,
    val writers: List<MovieHuman.Writer>
) {
    sealed interface MovieHuman {
        val id: Long
        val name: String
        val photoUrl: String

        data class Actor(
            override val id: Long,
            override val name: String,
            override val photoUrl: String,
            val character: String,
        ): MovieHuman

        data class Director(
            override val id: Long,
            override val name: String,
            override val photoUrl: String,
        ): MovieHuman

        data class Writer(
            override val id: Long,
            override val name: String,
            override val photoUrl: String,
        ): MovieHuman
    }
}