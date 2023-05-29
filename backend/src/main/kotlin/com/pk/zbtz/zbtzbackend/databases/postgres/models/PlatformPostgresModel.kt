package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models

import com.pk.zbtz.zbtzbackend.NoArg
import com.pk.zbtz.zbtzbackend.domain.Platform
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@NoArg
@Table(name = "platforms")
@Entity
class PlatformPostgresModel (
    val name: String,
    val logoUrl: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
) {
    fun toPlatfrom(): Platform {
        return Platform(
            id = id.toString(),
            name = name,
            logoUrl = logoUrl
        )
    }
}