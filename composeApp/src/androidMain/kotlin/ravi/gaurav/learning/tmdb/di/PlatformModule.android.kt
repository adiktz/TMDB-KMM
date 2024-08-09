package ravi.gaurav.learning.tmdb.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.util.DnsSelector
import ravi.gaurav.learning.tmdb.util.ScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.AndroidScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.SystemInsetsHelper
import ravi.gaurav.learning.tmdb.util.AndroidSystemInsetsHelperImpl

actual val platformModule: Module = module {
    /*single<HttpClientEngine> {
        OkHttp.create {
            OkHttpClient.Builder().dns(DnsSelector()).build()
        }
    }*/
    single<HttpClientEngine> { Android.create() }
    single<ScreenDimensionsHelper> { AndroidScreenDimensionsHelper(get()) }
    singleOf<SystemInsetsHelper>(::AndroidSystemInsetsHelperImpl)
}