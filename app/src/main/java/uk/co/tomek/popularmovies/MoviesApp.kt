package uk.co.tomek.popularmovies

import android.app.Application
import android.os.StrictMode
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import uk.co.tomek.popularmovies.di.applicationModule
import uk.co.tomek.popularmovies.di.networkModule

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            enableStrictMode()
        }

        startKoin {
            // Use Koin Android Logger
            androidLogger()
            // declare Android context
            androidContext(this@MoviesApp)
            // declare modules to use
            modules(
                listOf(
                    applicationModule,
                    networkModule
                )
            )
        }
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyFlashScreen()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build()
        )
    }
}
