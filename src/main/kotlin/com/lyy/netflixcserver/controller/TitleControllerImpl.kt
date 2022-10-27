/*
File: TitleControllerImpl.kt
Author: Kelvin LYY
Summary: Implementation of Title Controller
Description: Implementation of Title Controller
            - call getTrendingTitles from TitleService

*/

package com.lyy.netflixcserver.controller

import com.lyy.netflixcserver.controller.TitleControllerImpl.Companion.TITLE_BASE_URL
import com.lyy.netflixcserver.dto.title.FetchTitleDto
import com.lyy.netflixcserver.dto.title.TitleResponse
import com.lyy.netflixcserver.service.TitleService
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = [TITLE_BASE_URL])
class TitleControllerImpl(
    private val titleService: TitleService
) : TitleController {
    @Cacheable("poster")
    @GetMapping("/poster/{posterPath}", produces = [MediaType.IMAGE_PNG_VALUE])
    override fun getPoster(@PathVariable posterPath: String): ByteArray {
        return titleService.getPoster(posterPath)
    }

    @Cacheable("trendingTitles")
    @GetMapping("/trending/{mediaType}")
    override fun getTrendingTitles(@PathVariable mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        return titleService.getTrendingTitles(mediaType)
    }

    @Cacheable("topRatedTitles")
    @GetMapping("/top_rated/{mediaType}")
    override fun getTopRatedTitles(@PathVariable mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        return titleService.getTopRatedTitles(mediaType)
    }

    @Cacheable("upcomingTitles")
    @GetMapping("/upcoming")
    override fun getUpcomingMovies(): TitleResponse {
        return titleService.getUpcomingMovies()
    }

    @Cacheable("popularTitles")
    @GetMapping("/popular/{mediaType}")
    override fun getPopularTitles(@PathVariable mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        return titleService.getPopularTitles(mediaType)
    }


    companion object {
        const val TITLE_BASE_URL = "/api/title"
    }
}