package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.PlatformPostgresModel
import org.springframework.data.repository.CrudRepository

interface PlatformsPostgresRepository: CrudRepository<PlatformPostgresModel, Int> {

}