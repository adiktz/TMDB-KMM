package ravi.gaurav.learning.tmdb.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.util.UiDesignDecisionHelper
import ravi.gaurav.learning.tmdb.util.AndroidUiDesignDecisionHelper

actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    singleOf<UiDesignDecisionHelper>(::AndroidUiDesignDecisionHelper)
}