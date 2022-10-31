/*
File: TitleControllerImpl.kt
Author: Kelvin LYY
Summary: Implementation of Title Controller
Description: Implementation of Title Controller
            - use proxy to call get all Playlists in title service

*/

package com.lyy.netflixcserver.controller

import com.lyy.netflixcserver.controller.TitleControllerImpl.Companion.TITLE_BASE_URL
import com.lyy.netflixcserver.dto.title.FetchTitleDto
import com.lyy.netflixcserver.dto.title.TitleResponse
import com.lyy.netflixcserver.dto.title.TitleResponseWithAllPlaylists
import com.lyy.netflixcserver.service.TitleService
import com.lyy.netflixcserver.service.TitleServiceImpl
import org.springframework.aop.framework.ProxyFactory
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = [TITLE_BASE_URL])
class TitleControllerImpl(
    private val titleService: TitleService,
    private val titleServiceImpl: TitleServiceImpl,
) : TitleController {

    private val titleServiceProxyFactory = run {
        val factory = ProxyFactory(titleServiceImpl)
        factory.addInterface(TitleService::class.java)
        factory.addAdvice(CacheInterceptor())
        factory.isExposeProxy = true

        factory
    }

    @GetMapping("/poster/{posterPath}", produces = [MediaType.IMAGE_PNG_VALUE])
    override fun getPoster(@PathVariable posterPath: String): ByteArray {
        return titleService.getPoster(posterPath)
    }

    @GetMapping("/trending/{mediaType}")
    override fun getTrendingTitles(@PathVariable mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        return titleService.getTrendingTitles(mediaType)
    }

    @GetMapping("/top_rated/{mediaType}")
    override fun getTopRatedTitles(@PathVariable mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        return titleService.getTopRatedTitles(mediaType)
    }

    @GetMapping("/upcoming")
    override fun getUpcomingMovies(): TitleResponse {
        return titleService.getUpcomingMovies()
    }

    @GetMapping("/popular/{mediaType}")
    override fun getPopularTitles(@PathVariable mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        return titleService.getPopularTitles(mediaType)
    }

    @GetMapping("/all")
    override fun getAllPlaylists(): TitleResponseWithAllPlaylists {
        val titleService: TitleService = titleServiceProxyFactory.proxy as TitleService
        return titleService.getAllPlaylists()
    }

    companion object {
        const val TITLE_BASE_URL = "/api/title"
    }
}