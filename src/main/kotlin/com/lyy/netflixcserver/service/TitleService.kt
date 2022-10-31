/*
File: TitleService.kt
Author: Kelvin LYY
Summary: Interface of Title Service

*/

package com.lyy.netflixcserver.service

import com.lyy.netflixcserver.dto.title.FetchTitleDto
import com.lyy.netflixcserver.dto.title.TitleResponse
import com.lyy.netflixcserver.dto.title.TitleResponseWithAllPlaylists

interface TitleService {
    fun getPoster(posterPath: String): ByteArray
    fun getTrendingTitles(mediaType: FetchTitleDto.Companion.MediaType): TitleResponse
    fun getTopRatedTitles(mediaType: FetchTitleDto.Companion.MediaType): TitleResponse
    fun getUpcomingMovies(): TitleResponse
    fun getPopularTitles(mediaType: FetchTitleDto.Companion.MediaType): TitleResponse
    fun getAllPlaylists(): TitleResponseWithAllPlaylists

}