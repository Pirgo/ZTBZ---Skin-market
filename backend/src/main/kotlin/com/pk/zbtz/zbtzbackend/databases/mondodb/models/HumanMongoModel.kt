package com.pk.zbtz.zbtzbackend.databases.mondodb.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "people")
data class HumanMongoModel(
    @Id
    val id: String? = null,
    val firstName: String,
    val secondName: String,
    val photoUrl: String?,
    val birthday: LocalDate,
    val placeOfBirth: String,
    val deathDay: LocalDate?,
    val description: String,
    val functions: FunctionsValueMongo,
) {
    data class FunctionsValueMongo(
        val director: List<FunctionMongo.DirectorMongo>,
        val actor: List<FunctionMongo.ActorMongo>
    ) {
        sealed interface FunctionMongo {
            val filmId: String
            val title: String

            data class DirectorMongo(
                override val filmId: String,
                override val title: String,
            ) : FunctionMongo

            data class ActorMongo(
                override val filmId: String,
                override val title: String,
            ) : FunctionMongo
        }
    }
}