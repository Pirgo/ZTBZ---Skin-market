package com.pk.zbtz.zbtzbackend.databases.postgres.repositories

import com.pk.zbtz.zbtzbackend.databases.postgres.models.MoviePostgresModel
import org.springframework.data.jpa.repository.JpaRepository

interface MoviePostgresRepository: JpaRepository<MoviePostgresModel, Int> {

}