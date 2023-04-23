package com.pk.zbtz.zbtzbackend.databases.mondodb.repositories

import com.pk.zbtz.zbtzbackend.databases.mondodb.models.MovieMongoModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieMongoRepository : MongoRepository<MovieMongoModel, String>