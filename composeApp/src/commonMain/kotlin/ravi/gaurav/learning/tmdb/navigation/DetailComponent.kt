package ravi.gaurav.learning.tmdb.navigation

import com.arkivanov.decompose.ComponentContext
import ravi.gaurav.learning.tmdb.domain.Movie

class DetailComponent(
    componentContext: ComponentContext,
    val movie: Movie,
    val onBack:() -> Unit
): ComponentContext by componentContext {

    fun goBack() { onBack() }
}