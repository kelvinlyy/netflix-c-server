/*
File: TitleServiceImpl.kt
Author: Kelvin LYY
Summary: Implementation of Title Service
Description: Implementation of Title Service
            - get trending titles: fetch trending titles from TMDB and load into the format of TitleResponse

*/

package com.lyy.netflixcserver.service

import com.lyy.netflixcserver.dto.title.FetchTitleDto
import com.lyy.netflixcserver.dto.title.FetchTitleResponse
import com.lyy.netflixcserver.dto.title.TitleResponse
import com.lyy.netflixcserver.mapper.TitleMapper
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


@Service
class TitleServiceImpl(
    private val API_KEY: String = System.getenv("api_key"),
    private val titleMapper: TitleMapper,
    private val webClient: WebClient
) : TitleService {
    override fun getPoster(posterPath: String): ByteArray {
        println("Cache empty: sending API request...")

        return webClient.get()
            .uri("https://image.tmdb.org/t/p/w500/$posterPath")
            .accept(MediaType.IMAGE_JPEG)
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .block() ?: throw Exception("wrong type")

    }

    override fun getTrendingTitles(mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        println("Cache empty: sending API request...")

        val fetchTitleResponse = webClient.get()
            .uri("https://api.themoviedb.org/3/trending/$mediaType/day?api_key=$API_KEY")
            .retrieve()
            .bodyToMono(FetchTitleResponse::class.java)
            .block() ?: throw Exception("wrong type")

        return TitleResponse(fetchTitleResponse.results.map { titleMapper.fetchTitleDtoToTitleDto(it) })

    }

    override fun getTopRatedTitles(mediaType: FetchTitleDto.Companion.MediaType): TitleResponse {
        println("Cache empty: sending API request...")

        val fetchTitleResponse = webClient.get()
            .uri("https://api.themoviedb.org/3/$mediaType/top_rated?api_key=$API_KEY")
            .retrieve()
            .bodyToMono(FetchTitleResponse::class.java)
            .block() ?: throw Exception("wrong type")

        return TitleResponse(fetchTitleResponse.results.map { titleMapper.fetchTitleDtoToTitleDto(it) })

    }

    override fun getUpcomingMovies(): TitleResponse {
        println("Cache empty: sending API request...")

        val fetchTitleResponse = webClient.get()
            .uri("https://api.themoviedb.org/3/movie/upcoming?api_key=$API_KEY")
            .retrieve()
            .bodyToMono(FetchTitleResponse::class.java)
            .block() ?: throw Exception("wrong type")

        return TitleResponse(fetchTitleResponse.results.map { titleMapper.fetchTitleDtoToTitleDto(it) })

    }

}