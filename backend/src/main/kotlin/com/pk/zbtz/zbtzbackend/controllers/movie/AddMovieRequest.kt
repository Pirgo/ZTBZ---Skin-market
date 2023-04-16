package com.pk.zbtz.zbtzbackend.controllers.movie

data class AddMovieRequest(
    val title: String,
    val directorId: Int,
    val genreId: Int,
    val productionYear: Int?,
    val rating: Float?
)