/*
File: CachingConfig.kt
Author: Kelvin LYY
Summary: Configuration file for spring boot caching
Description: Configure for spring boot caching
            - initial caching capacity
            - maximum caching size
            - expiration time after write/access...

*/


package com.lyy.netflixcserver.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@EnableCaching
@Configuration
class CaffeineCacheConfig {
    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = CaffeineCacheManager(
            "trendingTitles",
            "topRatedTitles",
            "upcomingTitles",
            "poster"
        )
        cacheManager.setCaffeine(caffeineCacheBuilder())
        return cacheManager
    }

    fun caffeineCacheBuilder(): Caffeine<Any, Any> {
        return Caffeine.newBuilder()
//            .initialCapacity(100)
//            .maximumSize(500)
            .expireAfterWrite(1, TimeUnit.HOURS)
//            .weakKeys()
//            .recordStats()
    }
}