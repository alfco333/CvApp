package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.presenter.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockAppModule::class, MockDataModule::class])
interface MockAppComponent: AppComponent {
    override fun inject(mainActivity: MainActivity)
}