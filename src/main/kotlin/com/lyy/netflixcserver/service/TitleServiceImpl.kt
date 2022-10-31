/*
File: TitleServiceImpl.kt
Author: Kelvin LYY
Summary: Implementation of Title Service
Description: Implementation of Title Service with caching features
            - get all Playlists will self-invoke methods and use/store cache in the respective destination.
                - uses proxy to avoid self-invocation

*/

package com.lyy.netflixcserver.service

import com.lyy.netflixcserver.dto.title.FetchTitleDto
import com.lyy.netflixcserver.dto.title.FetchTitleResponse
import com.lyy.netflixcserver.dto.title.TitleResponse
import com.lyy.netflixcserver.dto.title.TitleResponseWithAllPlaylists
import com.lyy.netflixcserver.mapper.TitleMapper
import org.springframework.aop.framework.AopContext
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


@Service
class TitleServiceImpl(
    private val API_KEY: String = System.getenv("api_key"),
    private val titleMapper: TitleMapper,
    private val webClient: WebClient
) : TitleService {
    @Cacheable("poster")
    override fun getPoster(posterPath: String): ByteArray {
        println("Cache empty: sending API request...")

        return webClient.get()
            .uri("https://image.tmdb.org/t/p/w500/$posterPath")
            .accept(MediaType.IMAGE_JPEG)
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .block() ?: throw Exception("wrong type")

    }

    @Cacheable("trendingTitles")
    override fun getTrendingTitles(mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        println("Cache empty: sending API request...")

        val fetchTitleResponse = webClient.get()
            .uri("https://api.themoviedb.org/3/trending/$mediaType/day?api_key=$API_KEY")
            .retrieve()
            .bodyToMono(FetchTitleResponse::class.java)
            .block() ?: throw Exception("wrong type")

        return TitleResponse(fetchTitleResponse.results.map { titleMapper.fetchTitleDtoToTitleDto(it) })

    }

    @Cacheable("topRatedTitles")
    override fun getTopRatedTitles(mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        println("Cache empty: sending API request...")

        val fetchTitleResponse = webClient.get()
            .uri("https://api.themoviedb.org/3/$mediaType/top_rated?api_key=$API_KEY")
            .retrieve()
            .bodyToMono(FetchTitleResponse::class.java)
            .block() ?: throw Exception("wrong type")

        return TitleResponse(fetchTitleResponse.results.map { titleMapper.fetchTitleDtoToTitleDto(it) })

    }

    @Cacheable("upcomingTitles")
    override fun getUpcomingMovies(): TitleResponse {
        println("Cache empty: sending API request...")

        val fetchTitleResponse = webClient.get()
            .uri("https://api.themoviedb.org/3/movie/upcoming?api_key=$API_KEY")
            .retrieve()
            .bodyToMono(FetchTitleResponse::class.java)
            .block() ?: throw Exception("wrong type")

        return TitleResponse(fetchTitleResponse.results.map { titleMapper.fetchTitleDtoToTitleDto(it) })

    }

    @Cacheable("popularTitles")
    override fun getPopularTitles(mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        println("Cache empty: sending API request...")

        val fetchTitleResponse = webClient.get()
            .uri("https://api.themoviedb.org/3/$mediaType/popular?api_key=$API_KEY")
            .retrieve()
            .bodyToMono(FetchTitleResponse::class.java)
            .block() ?: throw Exception("wrong type")

        return TitleResponse(fetchTitleResponse.results.map { titleMapper.fetchTitleDtoToTitleDto(it) })

    }

    override fun getAllPlaylists(): TitleResponseWithAllPlaylists {
        // use proxy to prevent self-invocation of methods
        val currentProxy = AopContext.currentProxy() as TitleService

        val trendingMovies = currentProxy.getTrendingTitles(FetchTitleDto.Companion.MediaType.movie)
        val trendingTvs = currentProxy.getTrendingTitles(FetchTitleDto.Companion.MediaType.tv)
        val topRatedMovies = currentProxy.getTopRatedTitles(FetchTitleDto.Companion.MediaType.movie)
        val topRatedTvs = currentProxy.getTopRatedTitles(FetchTitleDto.Companion.MediaType.tv)
        val popularMovies = currentProxy.getPopularTitles(FetchTitleDto.Companion.MediaType.movie)
        val popularTvs = currentProxy.getPopularTitles(FetchTitleDto.Companion.MediaType.tv)
        val upcomingMovies = currentProxy.getUpcomingMovies()

        return TitleResponseWithAllPlaylists(
            trendingMovies = trendingMovies.results,
            trendingTvs = trendingTvs.results,
            topRatedMovies = topRatedMovies.results,
            topRatedTvs = topRatedTvs.results,
            popularMovies = popularMovies.results,
            popularTvs = popularTvs.results,
            upcomingMovies = upcomingMovies.results
        )
    }

}