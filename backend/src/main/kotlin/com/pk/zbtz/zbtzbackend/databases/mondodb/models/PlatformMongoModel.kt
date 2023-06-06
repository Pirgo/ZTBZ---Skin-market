package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.mondodb.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "platforms")
data class PlatformMongoModel(
    @Id
    val id: String? = null,
    val name: String,
    val logoUrl: String,
)
