package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models

import com.pk.zbtz.zbtzbackend.NoArg
import com.pk.zbtz.zbtzbackend.domain.Genre
import jakarta.persistence.*

@NoArg
@Table(name = "genres")
@Entity
class GenrePostgresModel(
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
) {
    fun toGenre(): Genre {
        return Genre(
            id = id.toString(),
            name = name
        )
    }
}