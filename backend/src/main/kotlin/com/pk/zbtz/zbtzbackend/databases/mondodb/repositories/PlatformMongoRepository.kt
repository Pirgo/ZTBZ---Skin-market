package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.mondodb.repositories

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.mondodb.models.PlatformMongoModel
import org.springframework.data.mongodb.repository.MongoRepository

interface PlatformMongoRepository : MongoRepository<PlatformMongoModel, String> {
    fun findAllByIdIn(ids: List<String>): List<PlatformMongoModel>
}