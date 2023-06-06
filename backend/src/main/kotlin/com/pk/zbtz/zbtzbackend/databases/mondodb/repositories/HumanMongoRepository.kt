package com.pk.zbtz.zbtzbackend.databases.mondodb.repositories

import com.pk.zbtz.zbtzbackend.databases.mondodb.models.HumanMongoModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface HumanMongoRepository : MongoRepository<HumanMongoModel, String> {

    @Query("{'\$or': [{'firstName': {\$regex: ?0, \$options: 'i'}}, {'secondName': {\$regex: ?0, \$options: 'i'}}]}")
    fun findByFirstNameOrSecondName(
        name: String,
        pageable: Pageable,
    ): Page<HumanMongoModel>

    @Query("{'\$or': [{'functions.director.filmId': ?0}, {'functions.actor.filmId': ?0}]}")
    fun findByFilmId(filmId: String): List<HumanMongoModel>
}