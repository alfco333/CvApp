package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockAppModule {

    @Provides
    @Singleton
    fun providesResourceManager(): ResourceManager {
        return MockResourceManager()
    }

    @Provides
    @Singleton
    fun providesResumeRepository(): ResumeRepository {
        return MockDataRepository()
    }
}