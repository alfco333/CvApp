package com.angelvargas.cvapp.di

import android.content.Context
import android.content.res.Resources
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import com.angelvargas.cvapp.managers.ResumeResourceManager
import com.angelvargas.cvapp.services.ImageService
import com.angelvargas.cvapp.services.PicassImageService
import com.angelvargas.data.database.LocalDataSource
import com.angelvargas.data.repository.ResumeDataRepository
import com.angelvargas.data.services.ResumeApiServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesAppContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    fun providesResourceManager(resources: Resources): ResourceManager {
        return ResumeResourceManager(resources)
    }

    @Provides
    @Singleton
    fun providesResumeRepository(resumeServices: ResumeApiServices,
                                 localDataSource: LocalDataSource): ResumeRepository {
        return ResumeDataRepository(resumeServices, localDataSource)
    }

    @Singleton
    @Provides
    fun providesImageService(): ImageService {
        return PicassImageService()
    }
}