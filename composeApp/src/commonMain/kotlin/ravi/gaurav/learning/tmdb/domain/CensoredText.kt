package ravi.gaurav.learning.tmdb.domain

import kotlinx.serialization.Serializable

@Serializable
data class CensoredText(
    val result: String = "",
    val error: String? = null
)