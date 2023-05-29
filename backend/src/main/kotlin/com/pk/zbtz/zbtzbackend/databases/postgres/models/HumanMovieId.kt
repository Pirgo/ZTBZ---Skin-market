package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models

import com.pk.zbtz.zbtzbackend.NoArg
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@NoArg
@Embeddable
class HumanMovieId(
    @Column(name = "humanId")
    val humanId: Int,

    @Column(name = "movieId")
    val movieId: Int
): Serializable