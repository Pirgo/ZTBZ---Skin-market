package com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.HumanPostgresModel
import java.time.LocalDate

data class AddHumanRequest(
    val firstName: String,
    val secondName: String,
    val photoUrl: String,
    val birthday: LocalDate,
    val placeOfBirth: String,
    val deathDay: LocalDate?,
    val description: String,
) {
    fun toPostgresHumanModel(): HumanPostgresModel {
        return HumanPostgresModel(
            firstName = firstName,
            secondName = secondName,
            photoUrl = photoUrl,
            birthday = birthday,
            placeOfBirth = placeOfBirth,
            deathDay = deathDay,
            description = description,
            directed = emptyList(),
            playedIn = emptyList(),
            id = null
        )
    }
}