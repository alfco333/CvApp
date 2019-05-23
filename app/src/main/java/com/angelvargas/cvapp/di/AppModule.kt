package com.angelvargas.cvapp.di

import android.content.Context
import android.content.res.Resources
import com.angelvargas.cvapp.domain.executor.PostExecutionThread
import com.angelvargas.cvapp.domain.executor.ThreadExecutor
import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.cvapp.managers.ResumeResourceManager
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
    fun providesGetResumeInfoUseCase(resumeRepository: ResumeRepository,
                                     threadExecutor: ThreadExecutor,
                                     postExecutionThread: PostExecutionThread): GetResumeInformationUseCase {
        return GetResumeInformationUseCase(resumeRepository, threadExecutor, postExecutionThread)
    }
}