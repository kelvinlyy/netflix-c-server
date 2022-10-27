/*
File: GenreService.kt
Author: Kelvin LYY
Summary: Interface of Genre Service
Description: Interface of Genre Service
            - fetch genres (movie and tv)

*/

package com.lyy.netflixcserver.service

import com.lyy.netflixcserver.dto.genre.FetchGenreResponse

interface GenreService {
    fun fetchGenreList(): FetchGenreResponse
}