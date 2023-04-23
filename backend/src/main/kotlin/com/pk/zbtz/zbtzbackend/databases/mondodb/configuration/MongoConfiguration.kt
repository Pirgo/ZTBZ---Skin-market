package com.pk.zbtz.zbtzbackend.databases.mondodb.configuration


import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@Configuration
class MongoConfiguration {

    @Bean
    fun mongoDatabaseFactory(): MongoDatabaseFactory {
        val connectionString = ConnectionString("mongodb://ztbzBackend:example@localhost:27017/ztbzDatabase")
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        val mongoClient = MongoClients.create(mongoClientSettings)

        return SimpleMongoClientDatabaseFactory(mongoClient, connectionString.database!!)
    }

    @Bean
    fun mongoTemplate(databaseFactory: MongoDatabaseFactory): MongoTemplate =
        MongoTemplate(databaseFactory)

    // Optional, if you need transaction support
    @Bean
    fun transactionManager(databaseFactory: MongoDatabaseFactory): MongoTransactionManager =
        MongoTransactionManager(databaseFactory)
}