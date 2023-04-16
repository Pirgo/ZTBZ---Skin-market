package com.pk.zbtz.zbtzbackend.controllers.genre

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.domain.Genre
import com.pk.zbtz.zbtzbackend.domain.Statistics
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/genres")
class GenreController {

    @GetMapping
    fun getGenres(): ResponseWithStatistics<List<Genre>> {
        // TODO: Implement

        return ResponseWithStatistics(
            data = emptyList(),
            statistics = Statistics()
        )
    }

    @PostMapping
    fun addGenre(
        @RequestBody request: AddGenreRequest,
    ): ResponseWithStatistics<Genre> {
        // TODO: Implement

        return ResponseWithStatistics(
            data = null,
            statistics = Statistics()
        )
    }

    @DeleteMapping("/{genre_id}")
    fun deleteMovie(
        @PathVariable("genre_id") genreId: Long,
    ): ResponseWithStatistics<Any> {
        // TODO: Implement

        return ResponseWithStatistics(
            data = null,
            statistics = Statistics()
        )
    }
}