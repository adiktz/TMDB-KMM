package ravi.gaurav.learning.tmdb.di

import org.koin.core.Koin
import org.koin.core.module.Module

fun initKoinDesktop(additionalModules: List<Module> = listOf()): Koin {
    return initKoin(additionalModules)
}