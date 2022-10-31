/*
File: FetchTitleDto.kt
Author: Kelvin LYY
Summary: Representation of each Title object fetched from external API

*/

package com.lyy.netflixcserver.dto.title

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty

data class FetchTitleDto(
    @JsonProperty("id")
    val id: Long = 1L,

    @JsonProperty("title_name")
    @JsonAlias("name", "title", "original_name", "original_title")
    val titleName: String = "",

    @JsonProperty("overview")
    val overview: String = "",

    @JsonProperty("release_date")
    var releaseDate: String = "",

    @JsonProperty("poster_path")
    val posterPath: String = "",

    @JsonProperty("vote_count")
    val voteCount: Long = 0,

    @JsonProperty("vote_average")
    val voteAverage: Double = 0.0,

    @JsonProperty("popularity")
    var popularity: Double = 0.0,

    @JsonProperty("genre_ids")
    var genreIds: MutableList<Long> = mutableListOf(),

    @JsonProperty("media_type")
    var mediaType: MediaType = MediaType.movie,

    ) {
    companion object {
        enum class MediaType {
            @JsonProperty("movie")
            movie,
            @JsonProperty("tv")
            tv
        }
    }
}
