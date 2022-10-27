/*
File: FetchGenreDto.kt
Author: Kelvin LYY
Summary: Representation of each Genre object fetched from external API

*/

package com.lyy.netflixcserver.dto.genre


data class FetchGenreDto(
    val id: Long = 1L,
    val name: String = ""
)