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
        value = "select * from movies order by rating desc offset (:offset) limit (:limit)",
        nativeQuery = true
    )
    fun findAllWithOffsetAndLimit(
        @Param("offset") offset: Int,
        @Param("limit") limit : Int
    ): List<MoviePostgresModel>

    @Query(
        value = "select * from movies where production_year = (:year) order by rating desc offset (:offset) limit (:limit)",
        nativeQuery = true
    )
    fun findAllByYearWithOffsetAndLimit(
        @Param("year") year: Int,
        @Param("offset") offset: Int,
        @Param("limit") limit : Int
    ): List<MoviePostgresModel>

    @Query(
        value =
        """
           select m.id, m.budget, m.cover_url, m.length, m.plot, m.production_year, m.rating, m.title from movies m inner join movie_platforms mp on m.id = mp.movie_id
           inner join platforms p on p.id = mp.platform_id where p.name = (:platform)
           order by rating desc
           offset (:offset) limit (:limit)
        """,
        nativeQuery = true
    )
    fun findAllByPlatformWithOffsetAndLimit(
        @Param("platform") platform: String,
        @Param("offset") offset: Int,
        @Param("limit") limit : Int
    ): List<MoviePostgresModel>

    @Query(
        value = "select * from movies where title ilike (:title) order by rating desc offset (:offset) limit (:limit)",
        nativeQuery = true
    )
    fun findAllByTitleWithOffsetAndLimit(
        @Param("title") title: String,
        @Param("offset") offset: Int,
        @Param("limit") limit : Int
    ): List<MoviePostgresModel>

    //kombinacje z title
    @Query(
        value = "select * from movies where title ilike (:title) and production_year = (:year) order by rating desc offset (:offset) limit (:limit)",
        nativeQuery = true
    )
    fun findAllByTitleAndYearWithOffsetAndLimit(
        @Param("title") title: String,
        @Param("year") year: Int,
        @Param("offset") offset: Int,
        @Param("limit") limit : Int
    ): List<MoviePostgresModel>

    @Query(
        value =
        """
           select m.id, m.budget, m.cover_url, m.length, m.plot, m.production_year, m.rating, m.title from movies m
           inner join movie_platforms mp on m.id = mp.movie_id
           inner join platforms p on p.id = mp.platform_id 
           where p.name = (:platform)  and m.title ilike (:title)
           order by m.rating desc 
           offset (:offset) limit (:limit)
        """,
        nativeQuery = true
    )
    fun findAllByTitleAndPlatformWithOffsetAndLimit(
        @Param("title") title: String,
        @Param("platform") platform: String,
        @Param("offset") offset: Int,
        @Param("limit") limit : Int
    ): List<MoviePostgresModel>

    //kombinacja year i platform
    @Query(
        value =
        """
           select m.id, m.budget, m.cover_url, m.length, m.plot, m.production_year, m.rating, m.title from movies m 
           inner join movie_platforms mp on m.id = mp.movie_id
           inner join platforms p on p.id = mp.platform_id 
           where p.name = (:platform) and m.production_year = (:year) 
           order by m.rating desc
           offset (:offset) limit (:limit)
        """,
        nativeQuery = true
    )
    fun findAllByPlatformAndYearWithOffsetAndLimit(
        @Param("platform") platform: String,
        @Param("year") year: Int,
        @Param("offset") offset: Int,
        @Param("limit") limit : Int
    ): List<MoviePostgresModel>

    //wszystko
    @Query(
        value =
        """
           select m.id, m.budget, m.cover_url, m.length, m.plot, m.production_year, m.rating, m.title from movies m 
           inner join movie_platforms mp on m.id = mp.movie_id
           inner join platforms p on p.id = mp.platform_id 
           where p.name = (:platform) and m.production_year = (:year) and m.title ilike (:title)
           order by m.rating desc
           offset (:offset) limit (:limit)
        """,
        nativeQuery = true
    )
    fun findAllByPlatformAndYearAndTitleWithOffsetAndLimit(
        @Param("platform") platform: String,
        @Param("year") year: Int,
        @Param("title") title: String,
        @Param("offset") offset: Int,
        @Param("limit") limit : Int
    ): List<MoviePostgresModel>
}