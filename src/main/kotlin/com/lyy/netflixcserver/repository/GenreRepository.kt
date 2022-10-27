/*
File: GenreRepository
Author: Kelvin LYY
Summary: JPA repository of Genre entity object
Description:    - CRUD functionality for Genre entity

*/

package com.lyy.netflixcserver.repository

import com.lyy.netflixcserver.entity.Genre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GenreRepository: JpaRepository<Genre, Long>