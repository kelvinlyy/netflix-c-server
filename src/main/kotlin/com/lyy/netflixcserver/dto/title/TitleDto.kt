/*
File: TitleDto.kt
Author: Kelvin LYY
Summary: Representation of each Title object that is returned to client upon request

*/

package com.lyy.netflixcserver.dto.title


data class TitleDto(
    var titleName: String = "",
    var overview: String = "",
    var releaseDate: String = "",
    var posterPath: String = "",
    var voteCount: Long = 0,
    var voteAverage: Double = 0.0,
    var popularity: Double = 0.0,
    var genres: List<String> = mutableListOf(),
    var mediaType: FetchTitleDto.Companion.MediaType = FetchTitleDto.Companion.MediaType.movie
)

