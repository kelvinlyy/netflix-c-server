/*
File: TitleDto.kt
Author: Kelvin LYY
Summary: Representation of Title Response object that is returned to client upon request

*/

package com.lyy.netflixcserver.dto.title

data class TitleResponse(
    val results: List<TitleDto> = listOf()
)