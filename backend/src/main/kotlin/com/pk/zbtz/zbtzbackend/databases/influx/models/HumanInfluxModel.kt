package com.pk.zbtz.zbtzbackend.databases.influx.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "people")
data class HumanInfluxModel(
    @Id
    val id: String? = null,
    val firstName: String,
    val secondName: String,
    val photoUrl: String?,
    val birthday: LocalDate,
    val placeOfBirth: String,
    val deathDay: LocalDate?,
    val description: String,
    val functions: FunctionsValueInflux,
) {
    data class FunctionsValueInflux(
        val director: List<FunctionInflux.DirectorInflux>,
        val actor: List<FunctionInflux.ActorInflux>
    ) {
        sealed interface FunctionInflux {
            val filmId: String
            val title: String

            data class DirectorInflux(
                override val filmId: String,
                override val title: String,
            ) : FunctionInflux

            data class ActorInflux(
                override val filmId: String,
                override val title: String,
            ) : FunctionInflux
        }
    }
}
