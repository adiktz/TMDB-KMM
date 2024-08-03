package ravi.gaurav.learning.tmdb.di

import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.di.platformComponent.DesktopComponent

fun initKoinDesktop(appComponent: DesktopComponent) {
    initKoin(
        listOf(module { single { appComponent } })
    )
}