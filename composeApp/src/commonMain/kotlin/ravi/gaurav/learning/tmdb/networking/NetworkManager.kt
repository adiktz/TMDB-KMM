package ravi.gaurav.learning.tmdb.networking

import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class NetworkManager(
    val networkClient: NetworkClient
) {

    suspend inline fun <reified T> makeRequest(
        url: String,
        method: HttpMethod = HttpMethod.Get,
        parameters: Map<String, Any> = mapOf(),
        body: Any? = null
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.client.request(url) {
                    this.method = method
                    parameters.entries.forEach {
                        this.parameter(it.key, it.value)
                    }
                    if (method == HttpMethod.Get && body != null) {
                        contentType(ContentType.Application.Json)
                        setBody(body)
                    }
                }
                Result.success(response.body())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


    internal suspend inline fun <reified T> get(
        url: String,
        parameters: Map<String, Any> = mapOf()
    ): Result<T> {
        return makeRequest(url, HttpMethod.Get, parameters)
    }

}