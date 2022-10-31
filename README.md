# Netflix C Server

An intermediate application server for fetching movie/tv data from TMDB. The application provides caching for data so 
that data fetching from TMDB is more efficient (less requests to the remote server and faster data response)

API endpoints: http://localhost:8080/swagger-ui.html

## Technical Problem

### Problem 1: Data retrieved by WebClient exceeds the upper size limit

Exception `org.springframework.core.io.buffer.DataBufferLimitException: Exceeded limit on max bytes to buffer : 262144`
raised when fetching large image with size larger than 256KB

#### Solution: 

In WebClientConfig, increase the maximum size limit in memory by setting exchangeStrategies.

```kotlin
@Bean
fun webClient(): WebClient {
    // set maxInMemorySize to 16MB
    val size = 16 * 1024 * 1024 // => 16MB
    val strategies = ExchangeStrategies.builder()
        .codecs { codecs: ClientCodecConfigurer ->
            codecs.defaultCodecs().maxInMemorySize(size)
        }
        .build()
    return WebClient.builder()
        .exchangeStrategies(strategies)
        .build()
}
```

### Problem 2: Data fetched from API is different to the data we want to return in our controller

In TMDB, genres in each title are represented in List of Integers in canonical format. Yet, we want to return the actual
genre values in our controller (List of String).

#### Solution: 

Make extra DTOs for fetching titles from TMDB for Jackson to deserialize, i.e. `FetchTitleDto` and `FetchTitleResponse`.
For mapping the fetching DTOs back to the DTOs desired, we use `mapstruct` mapper.


### Problem 3: Methods called by self-invocation could not use caching features

In `getAllPlaylists()` from `TitleServiceImpl.kt`, several cacheable methods from the same class are called, yet 
self-invocation is not going to result in the advice associated with a method invocation getting a chance to execute.

#### Solution: 

In `TitleControllerImpl.kt`, create a proxy where the target is `TitleServiceImpl.kt` and make the method 
`getAllPlaylists()` from `TitleServiceImpl.kt` to be called from the proxy. 

```kotlin
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
    
    ...
    
    override fun getAllPlaylists(): TitleResponseWithAllPlaylists {
        val titleService: TitleService = titleServiceProxyFactory.proxy as TitleService
        return titleService.getAllPlaylists()
    }
}

```

In addition, in `TitleServiceImpl`, when `getAllPlaylists()` is called, make use of the proxy made in the client caller,
`TitleControllerImpl.kt` to call the methods in the same class to avoid self-invocation.

```kotlin
val currentProxy = AopContext.currentProxy() as TitleService
val trendingMovies = currentProxy.getTrendingTitles(FetchTitleDto.Companion.MediaType.movie)
    ...
```

#### Reference: 
[Transactions, Caching and AOP: understanding proxy usage in Spring](https://spring.io/blog/2012/05/23/transactions-caching-and-aop-understanding-proxy-usage-in-spring)

[Proxying mechanisms](https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch08s06.html)