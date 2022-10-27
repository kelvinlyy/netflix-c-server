/*
File: FetchGenreResponse.kt
Author: Kelvin LYY
Summary: Representation of Genre Response object fetched from external API

*/

package com.lyy.netflixcserver.dto.genre


data class FetchGenreResponse(
    val genres: List<FetchGenreDto> = listOf()
)