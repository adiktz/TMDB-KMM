package ravi.gaurav.learning.tmdb.di
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.api.Service
import ravi.gaurav.learning.tmdb.api.Repository
import ravi.gaurav.learning.tmdb.networking.NetworkClient
import ravi.gaurav.learning.tmdb.networking.NetworkManager
import ravi.gaurav.learning.tmdb.util.SystemInsetsHelper
import ravi.gaurav.learning.tmdb.util.getSystemInsetsHelper

val commonModule = module {
    singleOf(::NetworkManager)
    singleOf(::NetworkClient)
    singleOf(::Service)
    singleOf(::Repository)
}