package com.alessandrofarandagancio.fitnessstudios

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}