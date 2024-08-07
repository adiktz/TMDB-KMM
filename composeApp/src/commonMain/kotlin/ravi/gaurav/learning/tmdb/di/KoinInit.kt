package ravi.gaurav.learning.tmdb.di

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    additionalModules: List<Module> = listOf(),
    appDeclaration: KoinAppDeclaration = {},
): Koin {
    return startKoin {
        appDeclaration()
        modules(
            additionalModules +
                    listOf(
                        commonModule,
                        platformModule,
                    )
        )
    }.koin
}
