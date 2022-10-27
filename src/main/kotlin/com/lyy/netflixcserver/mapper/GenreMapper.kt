/*
File: GenreMapper.kt
Author: Kelvin LYY
Summary: Interface of mapping genre-related data class
Description: Map FetchGenreDto to Genre entity

*/

package com.lyy.netflixcserver.mapper

import com.lyy.netflixcserver.dto.genre.FetchGenreDto
import com.lyy.netflixcserver.entity.Genre
import org.mapstruct.Mapper


@Mapper
interface GenreMapper {
    fun fetchGenreDtoToGenre(fetchGenreDto: FetchGenreDto): Genre
}