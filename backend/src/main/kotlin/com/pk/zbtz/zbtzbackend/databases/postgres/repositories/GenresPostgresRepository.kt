package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.GenrePostgresModel
import org.springframework.data.repository.CrudRepository

interface GenresPostgresRepository: CrudRepository<GenrePostgresModel, Int> {

}