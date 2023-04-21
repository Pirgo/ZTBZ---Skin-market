package com.pk.zbtz.zbtzbackend.controllers.movie

data class AddMovieRequest(
    val id: Long,
    val title: String,
    val platformIds: List<Long>,
    val genreIds: List<Long>,
    val productionYear: Int,
    val rating: Float?,
    val plot: String,
    val coverUrl: String,
    val budget: Long,
    val actors: List<AddMovieHuman.Actor>,
    val directors: List<AddMovieHuman.Director>,
    val writers: List<AddMovieHuman.Writer>
) {
    sealed interface AddMovieHuman {
        val id: Long

        data class Actor(
            override val id: Long,
            val character: String,
        ): AddMovieHuman

        data class Director(
            override val id: Long,
        ): AddMovieHuman

        data class Writer(
            override val id: Long,
        ): AddMovieHuman
    }
}