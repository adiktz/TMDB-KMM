package ravi.gaurav.learning.tmdb.util

import io.kamel.core.config.DefaultCacheSize
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.fileFetcher
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.imageBitmapDecoder
import io.kamel.image.config.imageVectorDecoder
import io.kamel.image.config.svgDecoder
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.CacheControl
import io.ktor.http.isSuccess
import io.ktor.http.maxAge

val customKamelConfig = KamelConfig {
    // Copies the default implementation if needed
    takeFrom(KamelConfig.Default)

    // Sets the number of images to cache
    imageBitmapCacheSize = DefaultCacheSize

    // adds an ImageBitmapDecoder
    imageBitmapDecoder()

    // adds an ImageVectorDecoder (XML)
    imageVectorDecoder()

    // adds an SvgDecoder (SVG)
    svgDecoder()

    // adds a FileFetcher
    fileFetcher()

    // Configures Ktor HttpClient
    httpFetcher {
        // httpCache is defined in kamel-core and configures the ktor client
        // to install a HttpCache feature with the implementation provided by Kamel.
        // The size of the cache can be defined in Bytes.
        httpCache(100 * 1024 * 1024  /* 10 MiB */)

//        defaultRequest {
//            url(Constants.imageBaseUrl)
//            CacheControl(CacheControl.MaxAge(maxAgeSeconds = 10000))
//        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { httpRequest, httpResponse ->
                !httpResponse.status.isSuccess()
            }
        }

        // Requires adding "io.ktor:ktor-client-logging:$ktor_version"
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    // more functionality available.
}
