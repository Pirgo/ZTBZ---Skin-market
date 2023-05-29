package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.HumanPostgresModel
import org.springframework.data.repository.CrudRepository

interface HumanPostgresRepository: CrudRepository<HumanPostgresModel, Int> {

}