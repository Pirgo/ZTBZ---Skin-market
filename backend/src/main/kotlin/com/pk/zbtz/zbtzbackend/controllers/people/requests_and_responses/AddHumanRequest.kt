package com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses

import java.time.LocalDate

data class AddHumanRequest(
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

            data class Director(
                override val filmId: String,
            ) : Function

            data class Actor(
                override val filmId: String,
            ) : Function
        }
    }
}
