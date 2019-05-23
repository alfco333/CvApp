package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.presenter.MainActivity
import com.angelvargas.data.di.DataModule
import com.angelvargas.data.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}