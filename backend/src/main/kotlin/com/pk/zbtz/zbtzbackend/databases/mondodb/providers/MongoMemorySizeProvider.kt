package com.pk.zbtz.zbtzbackend.databases.mondodb.providers

interface MongoMemorySizeProvider {
    fun getDatabaseSizeInGigabytes(): Double
}