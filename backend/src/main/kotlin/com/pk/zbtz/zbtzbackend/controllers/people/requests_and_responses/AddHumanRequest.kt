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
)