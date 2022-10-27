/*
File: Genre.kt
Author: Kelvin LYY
Summary: Representation of Genre object that is stored into the database

*/

package com.lyy.netflixcserver.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Genre(
    @Id
    var id: Long = 1L,
    var name: String = ""
) {

}
