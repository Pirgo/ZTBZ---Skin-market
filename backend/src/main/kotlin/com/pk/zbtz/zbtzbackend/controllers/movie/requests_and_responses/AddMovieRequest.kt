package com.pk.zbtz.zbtzbackend.controllers.movie.requests_and_responses

data class AddMovieRequest(
    val title: String,
    val platformIds: List<String>,
    val genreIds: List<String>,
    val productionYear: Int,
    val rating: Float?,
    val plot: String,
    val coverUrl: String,
    val budget: Float,
    val length: Float,
    val actors: List<AddMovieActor>,
    val directors: List<AddMovieDirector>,
) {
    data class AddMovieActor(
        val id: String,
        val character: String,
    )

    data class AddMovieDirector(
        val id: String,
    )
}