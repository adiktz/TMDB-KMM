package ravi.gaurav.learning.tmdb.networking

import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.platformType
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class NetworkManager(
    private val networkClient: NetworkClient
) {

    private suspend fun <T> makeRequest(
        typeInfo: TypeInfo,
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
                    if (method == HttpMethod.Post && body != null) {
                        contentType(ContentType.Application.Json)
                        setBody(body)
                    }
                }
                Result.success(response.body(typeInfo))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    internal suspend inline fun <reified T> get(
        url: String,
        parameters: Map<String, Any> = mapOf()
    ): Result<T> {
        return makeRequest(typeInfo<T>(), url, HttpMethod.Get, parameters)
    }

}