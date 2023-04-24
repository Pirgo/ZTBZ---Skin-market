package com.pk.zbtz.zbtzbackend.domain

data class MovieSummary(
    val id: String,
    val title: String,
    val productionYear: Int,
    val length: Float,
    val coverUrl: String,
    val genres: List<Genre>
)