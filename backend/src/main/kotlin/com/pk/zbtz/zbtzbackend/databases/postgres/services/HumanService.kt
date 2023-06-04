package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.services

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.HumanPostgresModel
import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories.HumanPostgresRepository
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import org.springframework.stereotype.Service

@Service
class HumanService(
    private val repository: HumanPostgresRepository
) {
    fun getById(id: Int): HumanPostgresModel? {
        return repository.findById(id).orElseThrow()
    }

    fun getAll(offset: Int): List<HumanPostgresModel> {
        return repository.findAllWithOffset(offset)
    }

    fun getAllByFirstNameOrSecondName(name: String, offset: Int): List<HumanPostgresModel> {
        return repository.findAllByFirstNameOrSecondName(name, offset)
    }

    fun save(human: HumanPostgresModel): HumanPostgresModel {
        return repository.save(human)
    }

    fun delete(id: Int) {
        return repository.deleteById(id)
    }

}