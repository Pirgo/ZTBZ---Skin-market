package com.pk.zbtz.zbtzbackend.databases.postgres.repositories

import com.pk.zbtz.zbtzbackend.databases.postgres.models.MoviePostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Year

@Repository
interface MoviePostgresRepository: JpaRepository<MoviePostgresModel, Int> {
    @Query(
        value = "select * from movies offset (:offset)",
        nativeQuery = true
    )
    fun findAllWithOffset(
        @Param("offset") offset: Int
    ): List<MoviePostgresModel>

    @Query(
        value = "select * from movies where production_year = (:year) offset (:offset)",
        nativeQuery = true
    )
    fun findAllByYearWithOffset(
        @Param("year") year: Int,
        @Param("offset") offset: Int
    ): List<MoviePostgresModel>

    @Query(
        value =
        """
           select m.id, m.budget, m.cover_url, m.length, m.plot, m.production_year, m.rating, m.title from movies m inner join movie_platforms mp on m.id = mp.movie_id
           inner join platforms p on p.id = mp.platform_id where p.name = (:platform) offset (:offset) 
        """,
        nativeQuery = true
    )
    fun findAllByPlatformWithOffset(
        @Param("platform") platform: String,
        @Param("offset") offset: Int
    ): List<MoviePostgresModel>

    @Query(
        value = "select * from movies where title ilike (:title) offset (:offset)",
        nativeQuery = true
    )
    fun findAllByTitleWithOffset(
        @Param("title") title: String,
        @Param("offset") offset: Int
    ): List<MoviePostgresModel>

    //kombinacje z title
    @Query(
        value = "select * from movies where title ilike (:title) and production_year = (:year) offset (:offset)",
        nativeQuery = true
    )
    fun findAllByTitleAndYearWithOffset(
        @Param("title") title: String,
        @Param("year") year: Int,
        @Param("offset") offset: Int
    ): List<MoviePostgresModel>

    @Query(
        value =
        """
           select m.id, m.budget, m.cover_url, m.length, m.plot, m.production_year, m.rating, m.title from movies m
           inner join movie_platforms mp on m.id = mp.movie_id
           inner join platforms p on p.id = mp.platform_id 
           where p.name = (:platform)  and m.title ilike (:title)
           offset (:offset) 
        """,
        nativeQuery = true
    )
    fun findAllByTitleAndPlatformWithOffset(
        @Param("title") title: String,
        @Param("platform") platform: String,
        @Param("offset") offset: Int
    ): List<MoviePostgresModel>

    //kombinacja year i platform
    @Query(
        value =
        """
           select m.id, m.budget, m.cover_url, m.length, m.plot, m.production_year, m.rating, m.title from movies m 
           inner join movie_platforms mp on m.id = mp.movie_id
           inner join platforms p on p.id = mp.platform_id 
           where p.name = (:platform) and m.production_year = (:year) 
           offset (:offset) 
        """,
        nativeQuery = true
    )
    fun findAllByPlatformAndYearWithOffset(
        @Param("platform") platform: String,
        @Param("year") year: Int,
        @Param("offset") offset: Int
    ): List<MoviePostgresModel>

    //wszystko
    @Query(
        value =
        """
           select m.id, m.budget, m.cover_url, m.length, m.plot, m.production_year, m.rating, m.title from movies m 
           inner join movie_platforms mp on m.id = mp.movie_id
           inner join platforms p on p.id = mp.platform_id 
           where p.name = (:platform) and m.production_year = (:year) and m.title ilike (:title)
           offset (:offset) 
        """,
        nativeQuery = true
    )
    fun findAllByPlatformAndYearAndTitleWithOffset(
        @Param("platform") platform: String,
        @Param("year") year: Int,
        @Param("title") title: String,
        @Param("offset") offset: Int
    ): List<MoviePostgresModel>
}