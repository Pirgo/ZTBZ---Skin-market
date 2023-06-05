package com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.repositories

import com.pk.zbtz.zbtzbackend.com.pk.zbtz.zbtzbackend.databases.postgres.models.HumanPostgresModel
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface HumanPostgresRepository: CrudRepository<HumanPostgresModel, Int> {
    //it is case sensitive
    @Query(
        value = "select * from humans where first_name in (:name) or second_name in (:name) offset (:offset) limit (:limit)",
        nativeQuery = true
    )
    fun findAllByFirstNameOrSecondName(
        @Param("name") name: String,
        @Param("offset") offset: Int,
        @Param("limit") limit: Int
    ): List<HumanPostgresModel>

    @Query(
        value = "select * from humans offset (:offset) limit (:limit)",
        nativeQuery = true
    )
    fun findAllWithOffset(
        @Param("offset") offset: Int,
        @Param("limit") limit: Int
    ): List<HumanPostgresModel>
}