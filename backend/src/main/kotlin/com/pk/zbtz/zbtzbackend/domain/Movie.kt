package com.pk.zbtz.zbtzbackend.domain

data class Movie(
    val id: Long,
    val title: String,
    val directorId: Long,
    val genreId: Long,
    val productionYear: Int?,
    val rating: Float?
)