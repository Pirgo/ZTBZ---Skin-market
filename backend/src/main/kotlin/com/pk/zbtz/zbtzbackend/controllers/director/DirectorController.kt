package com.pk.zbtz.zbtzbackend.controllers.director

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.domain.Director
import com.pk.zbtz.zbtzbackend.domain.Statistics
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/directors")
class DirectorController {

    @GetMapping
    fun getDirectors(): ResponseWithStatistics<List<Director>> {
        // TODO: Implement

        return ResponseWithStatistics(
            data = emptyList(), statistics = Statistics()
        )
    }

    @PostMapping
    fun addDirector(
        @RequestBody request: AddDirectorRequest,
    ): ResponseWithStatistics<Director> {
        // TODO: Implement

        return ResponseWithStatistics(
            data = null, statistics = Statistics()
        )
    }

    @DeleteMapping("/{director_id}")
    fun deleteMovie(
        @PathVariable("director_id") directorId: Long,
    ): ResponseWithStatistics<Any> {
        // TODO: Implement

        return ResponseWithStatistics(
            data = null, statistics = Statistics()
        )
    }
}