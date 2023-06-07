package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.mondodb.repositories

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.mondodb.models.GenreMongoModel
import org.springframework.data.mongodb.repository.MongoRepository

interface GenreMongoRepository : MongoRepository<GenreMongoModel, String>{
    fun findAllByIdIn(ids: List<String>): List<GenreMongoModel>
}