package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.presenter.MainActivity
import com.angelvargas.data.di.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockAppModule::class, DataModule::class])
interface MockAppComponent: AppComponent {
    override fun inject(mainActivity: MainActivity)
}