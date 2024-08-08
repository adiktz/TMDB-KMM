package ravi.gaurav.learning.tmdb.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.util.IosSystemInsetsHelper
import ravi.gaurav.learning.tmdb.util.ScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.IosScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.SystemInsetsHelper

actual val platformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    singleOf<ScreenDimensionsHelper>(::IosScreenDimensionsHelper)
    singleOf<SystemInsetsHelper>(::IosSystemInsetsHelper)
}