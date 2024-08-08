package ravi.gaurav.learning.tmdb.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.util.UiDesignDecisionHelper
import ravi.gaurav.learning.tmdb.util.AndroidUiDesignDecisionHelper
import ravi.gaurav.learning.tmdb.util.DnsSelector
import ravi.gaurav.learning.tmdb.util.ScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.ScreenDimensionsHelperImpl

actual val platformModule: Module = module {
    single<HttpClientEngine> {
        OkHttp.create {
            OkHttpClient.Builder().dns(DnsSelector()).build()
        }
    }
    singleOf<UiDesignDecisionHelper>(::AndroidUiDesignDecisionHelper)
    single<ScreenDimensionsHelper> { ScreenDimensionsHelperImpl(get()) }
}