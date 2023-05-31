package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.ActorPostgresModel
import org.springframework.data.repository.CrudRepository

interface ActorPostgresRepository: CrudRepository<ActorPostgresModel, Int> {

}