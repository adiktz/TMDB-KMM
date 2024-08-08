package ravi.gaurav.learning.tmdb.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.util.DesktopSystemInsetsHelper
import ravi.gaurav.learning.tmdb.util.ScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.DesktopScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.SystemInsetsHelper

actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    singleOf<ScreenDimensionsHelper>(::DesktopScreenDimensionsHelper)
    singleOf<SystemInsetsHelper>(::DesktopSystemInsetsHelper)

}