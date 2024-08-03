package ravi.gaurav.learning.tmdb

import android.app.Application
import org.koin.android.ext.koin.androidContext
import ravi.gaurav.learning.tmdb.di.initKoinAndroid
import ravi.gaurav.learning.tmdb.di.platformComponent.AndroidComponent


class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoinAndroid(appComponent = AndroidComponent()) {
            androidContext(this@MyApp)
        }
    }
}