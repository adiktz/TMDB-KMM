package ravi.gaurav.learning.tmdb.di

import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.di.platformComponent.IosComponent

fun initKoinIos(appComponent: IosComponent) {
    initKoin(
        listOf(module { single { appComponent } })
    )
}