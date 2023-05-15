package com.pk.zbtz.zbtzbackend.databases.mondodb.repositories

import com.pk.zbtz.zbtzbackend.databases.mondodb.models.MovieMongoModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MovieMongoRepository : MongoRepository<MovieMongoModel, String> {
    @Query("{ \$and: [ { 'title': { \$regex: ?0, \$options: 'i' } }, { 'platforms.name': { \$regex: ?1, \$options: 'i' } }, { 'productionYear': ?2 } ] }")
    fun findAllByFilters(
        titleToSearch: String,
        platformName: String,
        year: Int,
        pageable: Pageable
    ): Page<MovieMongoModel>

    @Query("{ \$and: [ { 'title': { \$regex: ?0, \$options: 'i' } }, { 'platforms.name': { \$regex: ?1, \$options: 'i' } } ] }")
    fun findAllByFilters(
        titleToSearch: String,
        platformName: String,
        pageable: Pageable
    ): Page<MovieMongoModel>

    @Query("{ 'platforms.id': { \$in: ?0 } }")
    fun findAllByPlatformIds(platformIds: List<String>): List<MovieMongoModel>

    @Query("{ 'genres.id': { \$in: ?0 } }")
    fun findAllByGenreIds(genreId: List<String>): List<MovieMongoModel>
}