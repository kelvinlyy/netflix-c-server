/*
File: FetchTitleResponse.kt
Author: Kelvin LYY
Summary: Representation of Title Response object fetched from external API

*/

package com.lyy.netflixcserver.dto.title

data class FetchTitleResponse(
    val results: List<FetchTitleDto> = listOf()
)
