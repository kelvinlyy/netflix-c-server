/*
File: TitleMapper.kt
Author: Kelvin LYY
Summary: Interface of mapping title-related data class
Description: Map FetchTitleDto to TitleDto, especially the genre ids in FetchTitleDto to genre names in TitleDto

*/

package com.lyy.netflixcserver.mapper

import com.lyy.netflixcserver.dto.title.FetchTitleDto
import com.lyy.netflixcserver.dto.title.TitleDto
import com.lyy.netflixcserver.repository.GenreRepository
import org.mapstruct.BeforeMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.springframework.beans.factory.annotation.Autowired

@Mapper
abstract class TitleMapper {

    @Autowired
    lateinit var genreRepository: GenreRepository

    @Mapping(target = "genres", ignore = true)
    abstract fun fetchTitleDtoToTitleDto(fetchTitleDto: FetchTitleDto): TitleDto

    @BeforeMapping
    fun mapGenreIdsToGenre(fetchTitleDto: FetchTitleDto, @MappingTarget titleDto: TitleDto) {
        val genreMap = this.getGenreMap()
        titleDto.genres = fetchTitleDto.genreIds.map {
            genreMap[it] ?: throw Exception("Genre Id $it not found")
        }
    }

    private fun getGenreMap(): MutableMap<Long, String> {
        val genreMap = mutableMapOf<Long, String>()
        genreRepository.findAll().map {
            genreMap[it.id] = it.name
        }
        return genreMap
    }

}