package ravi.gaurav.learning.tmdb.di

import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.di.platformComponent.AndroidComponent

fun initKoinAndroid(
    appComponent: AndroidComponent,
    appDeclaration: KoinAppDeclaration = {},
) {
    initKoin(
        listOf(module { single { appComponent } }),
        appDeclaration,
    )
}
