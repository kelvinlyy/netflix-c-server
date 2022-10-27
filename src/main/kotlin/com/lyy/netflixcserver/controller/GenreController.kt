/*
File: GenreController.kt
Author: Kelvin LYY
Summary: Interface of Genre Controller
Description: Interface of Genre Controller
            - fetch all genres mapping (id to name) (Only called to manually genre list)

*/

package com.lyy.netflixcserver.controller

import com.lyy.netflixcserver.dto.genre.FetchGenreResponse

interface GenreController {
    fun fetchGenreList(): FetchGenreResponse
}