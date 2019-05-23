package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.MainActivity
import com.angelvargas.data.di.DataModule
import com.angelvargas.data.network.ApiServiceFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ApiServiceFactory::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}