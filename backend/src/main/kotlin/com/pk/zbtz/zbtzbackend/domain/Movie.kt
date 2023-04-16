package com.pk.zbtz.zbtzbackend.domain

data class Movie(
    val id: Int,
    val title: String,
    val directorId: Int,
    val genreId: Int,
    val productionYear: Int?,
    val rating: Float?
)