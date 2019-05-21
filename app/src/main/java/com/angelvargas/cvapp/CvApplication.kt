package com.angelvargas.cvapp

import android.app.Application
import com.facebook.stetho.Stetho

class CvApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}