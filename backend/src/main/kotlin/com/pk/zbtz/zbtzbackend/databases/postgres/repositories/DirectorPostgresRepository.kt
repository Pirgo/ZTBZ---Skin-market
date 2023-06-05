package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.DirectorPostgresModel
import org.springframework.data.repository.CrudRepository

interface DirectorPostgresRepository: CrudRepository<DirectorPostgresModel, Int> {

}