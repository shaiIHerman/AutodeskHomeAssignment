package com.shai.autodesk.app

import android.app.Application
import com.amitshekhar.DebugDB
import com.shai.autodesk.app.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(repositoryModule, netModule, apiModule, databaseModule, viewModelModule))
        }

        Timber.plant(Timber.DebugTree())
        Timber.d("RoomDebug - %s", DebugDB.getAddressLog())
    }
}