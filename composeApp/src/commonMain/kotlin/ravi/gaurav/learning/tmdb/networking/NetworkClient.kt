package ravi.gaurav.learning.tmdb.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


class NetworkClient(
    engine: HttpClientEngine
) {
    @OptIn(ExperimentalSerializationApi::class)
    val client: HttpClient = HttpClient(engine) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    explicitNulls = false
                }
            )
        }
        expectSuccess = true
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, request ->
                val exceptionResponse =
                    when (exception) {
                        //4xx
                        is ClientRequestException -> {
                            exception.response.bodyAsText()
                        }
                        //3xx
                        is RedirectResponseException -> {
                            exception.response.bodyAsText()
                        }
                        //5xx
                        is ServerResponseException -> {
                            exception.response.bodyAsText()
                        }

                        else -> { "Unknown Error..."}
                    }
                throw Exception(exceptionResponse)
            }
        }
    }
}