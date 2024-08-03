package ravi.gaurav.learning.tmdb.api

import io.ktor.http.HttpMethod
import ravi.gaurav.learning.tmdb.networking.NetworkManager

class Service(
    val networkManager: NetworkManager
) {

    suspend fun getKtor(): Result<String> {
        val url = "https://ktor.io/docs/"
        return networkManager.get<String>(url)
    }

}