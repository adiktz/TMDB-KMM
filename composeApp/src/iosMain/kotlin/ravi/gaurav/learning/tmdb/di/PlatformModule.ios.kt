package ravi.gaurav.learning.tmdb.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.util.IosUiDesignDecisionHelper
import ravi.gaurav.learning.tmdb.util.UiDesignDecisionHelper

actual val platformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    singleOf<UiDesignDecisionHelper>(::IosUiDesignDecisionHelper)
}