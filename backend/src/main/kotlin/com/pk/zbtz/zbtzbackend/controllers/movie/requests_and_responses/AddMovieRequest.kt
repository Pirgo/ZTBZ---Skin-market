package com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses

data class AddMovieRequest(
    val id: String,
    val title: String,
    val platformIds: List<String>,
    val genreIds: List<String>,
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
        val id: String

        data class Actor(
            override val id: String,
            val character: String,
        ): AddMovieHuman

        data class Director(
            override val id: String,
        ): AddMovieHuman

        data class Writer(
            override val id: String,
        ): AddMovieHuman
    }
}