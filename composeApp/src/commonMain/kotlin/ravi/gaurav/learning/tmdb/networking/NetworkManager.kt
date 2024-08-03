package ravi.gaurav.learning.tmdb.networking

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.parametersOf
import io.ktor.util.toMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

class NetworkManager(
    val networkClient: NetworkClient
) {

    suspend inline fun <reified T>makeRequest(
        url: String,
        method: HttpMethod = HttpMethod.Get,
        parameters: Parameters = parametersOf()
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.client.get(urlString = url) {
                    parameters.forEach { key, value ->
                        this.parameter(key, value)
                    }
                }
                Result.success(response.body())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


    internal suspend inline fun <reified T>get(
        url: String,
        parameters: Map<String, Any> = mapOf()
    ): Result<T> {
        return withContext(Dispatchers.Main) {
            makeRequest(url, HttpMethod.Get, parameters as Parameters)
        }
    }

}