package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockAppModule(private val mockDataRepository: ResumeRepository,
                    private val mockResourceManager: ResourceManager) {

    @Provides
    @Singleton
    fun providesResourceManager(): ResourceManager {
        return mockResourceManager
    }

    @Provides
    @Singleton
    fun providesResumeRepository(): ResumeRepository {
        return mockDataRepository
    }
}