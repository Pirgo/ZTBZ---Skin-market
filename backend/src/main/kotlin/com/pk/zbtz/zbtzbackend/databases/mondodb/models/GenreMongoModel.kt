package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.mondodb.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "genres")
data class GenreMongoModel(
    @Id
    val id: String? = null,
    val name: String,
)
