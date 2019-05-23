package com.angelvargas.cvapp

import android.app.Application
import com.angelvargas.cvapp.di.AppComponent
import com.angelvargas.cvapp.di.AppModule
import com.angelvargas.cvapp.di.DaggerAppComponent
import com.angelvargas.data.di.DataModule
import com.angelvargas.data.network.ApiServiceFactory
import com.angelvargas.data.services.ResumeApiServices.Companion.BASE_URL
import com.facebook.stetho.Stetho
import io.realm.Realm

class CvApplication: Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        Realm.init(this)
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .apiServiceFactory(ApiServiceFactory(BASE_URL))
            .build()
    }

    internal fun getAppComponent(): AppComponent {
        return appComponent
    }
}