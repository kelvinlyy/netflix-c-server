/*
File: GenreControllerImpl.kt
Author: Kelvin LYY
Summary: Implementation of Genre Controller
Description: Implementation of Genre Controller
            - fetch all genres mapping (id to name) (Only called to manually genre list)

*/

package com.lyy.netflixcserver.controller

import com.lyy.netflixcserver.controller.GenreControllerImpl.Companion.GENRE_BASE_URL
import com.lyy.netflixcserver.dto.genre.FetchGenreResponse
import com.lyy.netflixcserver.service.GenreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = [GENRE_BASE_URL])
class GenreControllerImpl(
    private val genreService: GenreService
) : GenreController {
    @GetMapping("/genre")
    override fun fetchGenreList(): FetchGenreResponse {
        return genreService.fetchGenreList()
    }

    companion object {
        const val GENRE_BASE_URL = "/api/genre"
    }

}