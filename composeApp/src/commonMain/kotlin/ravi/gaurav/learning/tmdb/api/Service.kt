package ravi.gaurav.learning.tmdb.api

import io.ktor.http.parameters
import ravi.gaurav.learning.tmdb.domain.CensoredText
import ravi.gaurav.learning.tmdb.networking.NetworkManager

class Service(
    val networkManager: NetworkManager
) {

    suspend fun getKtor(): Result<String> {
        val url = "https://ktor.io/docs/"
        return networkManager.get<String>(url)
    }
    suspend fun getCensoredText(text: String): Result<CensoredText> {
        val url = "https://www.purgomalum.com/service/json"
        val parameter: Map<String,String> = mapOf("text" to text)
        return networkManager.get<CensoredText>(url, parameter)
    }
}