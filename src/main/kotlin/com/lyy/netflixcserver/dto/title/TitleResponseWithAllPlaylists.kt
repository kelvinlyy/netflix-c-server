package com.lyy.netflixcserver.dto.title

data class TitleResponseWithAllPlaylists(
    val trendingMovies: List<TitleDto> = listOf(),
    val trendingTvs: List<TitleDto> = listOf(),

    val topRatedMovies: List<TitleDto> = listOf(),
    val topRatedTvs: List<TitleDto> = listOf(),

    val popularMovies: List<TitleDto> = listOf(),
    val popularTvs: List<TitleDto> = listOf(),

    val upcomingMovies: List<TitleDto> = listOf(),
)
