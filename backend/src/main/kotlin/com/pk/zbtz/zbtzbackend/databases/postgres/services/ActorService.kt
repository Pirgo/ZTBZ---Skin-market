package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.services

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.ActorPostgresModel
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories.ActorPostgresRepository
import org.springframework.stereotype.Service

@Service
class ActorService(
    private val repository: ActorPostgresRepository
) {
    fun save(actor: ActorPostgresModel): ActorPostgresModel {
        return repository.save(actor)
    }
}