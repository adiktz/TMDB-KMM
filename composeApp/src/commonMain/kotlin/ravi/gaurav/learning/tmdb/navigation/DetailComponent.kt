package ravi.gaurav.learning.tmdb.navigation

import com.arkivanov.decompose.ComponentContext

class DetailComponent(
    componentContext: ComponentContext,
    val id: String,
    val onBack:() -> Unit
): ComponentContext by componentContext {

    fun goBack() { onBack() }
}