package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.services

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.DirectorPostgresModel
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories.DirectorPostgresRepository
import org.springframework.stereotype.Service

@Service
class DirectorService(
    private val repository: DirectorPostgresRepository
) {
    fun save(director: DirectorPostgresModel): DirectorPostgresModel {
        return repository.save(director)
    }
}