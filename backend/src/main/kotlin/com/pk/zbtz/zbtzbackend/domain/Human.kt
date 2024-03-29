package com.pk.zbtz.zbtzbackend.domain

import java.time.LocalDate

data class Human(
    val id: String,
    val firstName: String,
    val secondName: String,
    val photoUrl: String?,
    val birthday: LocalDate,
    val placeOfBirth: String,
    val deathDay: LocalDate?,
    val description: String,
    val functions: FunctionsValue,
) {
    data class FunctionsValue(
        val director: List<Function.Director>,
        val actor: List<Function.Actor>
    ) {
        sealed interface Function {
            val filmId: String
            val title: String

            data class Director(
                override val filmId: String,
                override val title: String,
            ) : Function

            data class Actor(
                override val filmId: String,
                override val title: String,
            ) : Function
        }
    }
}