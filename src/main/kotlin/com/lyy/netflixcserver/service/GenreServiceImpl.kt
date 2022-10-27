/*
File: GenreServiceImpl.kt
Author: Kelvin LYY
Summary: Implementation of Genre Service
Description: Implementation of Genre Service
            - fetch genres (movie and tv)

*/

package com.lyy.netflixcserver.service

import com.lyy.netflixcserver.dto.genre.FetchGenreResponse
import com.lyy.netflixcserver.mapper.GenreMapper
import com.lyy.netflixcserver.repository.GenreRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import javax.annotation.PostConstruct

@Service
class GenreServiceImpl(
    private val API_KEY: String = System.getenv("api_key"),
    private val genreRepository: GenreRepository,
    private val genreMapper: GenreMapper
) : GenreService {

    @PostConstruct
    override fun fetchGenreList(): FetchGenreResponse {
        val webClient = WebClient.create()

        val movieGenreList = webClient.get()
            .uri("https://api.themoviedb.org/3/genre/movie/list?api_key=$API_KEY")
            .retrieve()
            .bodyToMono(FetchGenreResponse::class.java)
            .block() ?: throw ClassNotFoundException("Resources of movie genre list not found")

        val tvGenreList = webClient.get()
            .uri("https://api.themoviedb.org/3/genre/tv/list?api_key=$API_KEY")
            .retrieve()
            .bodyToMono(FetchGenreResponse::class.java)
            .block() ?: throw ClassNotFoundException("Resources of tv genre list not found")

        val allGenreList = movieGenreList.genres + tvGenreList.genres
        genreRepository.saveAll(allGenreList.map { genreMapper.fetchGenreDtoToGenre(it) })

        return FetchGenreResponse(allGenreList)

    }


}