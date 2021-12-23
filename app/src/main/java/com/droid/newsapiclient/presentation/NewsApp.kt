package com.droid.newsapiclient.presentation

import android.app.Application
import android.os.StrictMode
import com.droid.newsapiclient.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            enableStrictMode()
        }
    }

    private fun enableStrictMode() {
        val policy = StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
        StrictMode.setThreadPolicy(policy)
    }
}