/*
File: NetflixCServerApplication.kt
Author: Kelvin LYY
Summary: Main file for starting the spring boot application

*/

package com.lyy.netflixcserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NetflixCServerApplication

fun main(args: Array<String>) {
    runApplication<NetflixCServerApplication>(*args)
}
