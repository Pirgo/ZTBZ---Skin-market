package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models

import com.pk.zbtz.zbtzbackend.NoArg
import com.pk.zbtz.zbtzbackend.databases.postgres.models.MoviePostgresModel
import com.pk.zbtz.zbtzbackend.domain.Human
import jakarta.persistence.*
import java.time.LocalDate

@NoArg
@Table(name = "humans")
@Entity
class HumanPostgresModel(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    val firstName: String,
    val secondName: String,
    val photoUrl: String,
    val birthday: LocalDate,
    val placeOfBirth: String,
    val deathDay: LocalDate?,
    val description: String,
    @OneToMany(mappedBy = "human", cascade = [CascadeType.ALL])
    val playedIn: List<ActorPostgresModel>,
    @OneToMany(mappedBy = "human", cascade = [CascadeType.ALL])
    val directed: List<DirectorPostgresModel>,
) {
    fun toHuman(): Human {
        return Human(
            id = id.toString(),
            firstName = firstName,
            secondName = secondName,
            photoUrl = photoUrl,
            birthday = birthday,
            placeOfBirth = placeOfBirth,
            deathDay = deathDay,
            description = description,
            functions = Human.FunctionsValue(
                actor = playedIn.map { Human.FunctionsValue.Function.Actor(it.movie.id.toString(), it.movie.title) },
                director = directed.map { Human.FunctionsValue.Function.Director(it.movie.id.toString(), it.movie.title) }
            )
        )
    }
}