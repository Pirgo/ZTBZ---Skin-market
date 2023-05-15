package com.pk.zbtz.zbtzbackend.databases.mondodb.providers

import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class MongoMemorySizeProviderImpl(
    private val mongoTemplate: MongoTemplate
) : MongoMemorySizeProvider {
    override fun getDatabaseSizeInGigabytes(): Double {
        val command = Document("dbStats", 1)
        val result = mongoTemplate.executeCommand(command)

        val dataSizeBytes = result.getDouble("dataSize")
        return dataSizeBytes / (1024.0 * 1024.0 * 1024.0) // Convert bytes to gigabytes
    }
}