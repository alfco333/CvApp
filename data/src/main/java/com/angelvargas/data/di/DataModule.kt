package com.angelvargas.data.di

import com.angelvargas.cvapp.domain.executor.PostExecutionThread
import com.angelvargas.cvapp.domain.executor.ThreadExecutor
import com.angelvargas.data.database.DatabaseProvider
import com.angelvargas.data.database.LocalDataSource
import com.angelvargas.data.database.RealmProvider
import com.angelvargas.data.database.RealmResumeDataSource
import com.angelvargas.data.executor.JobExecutor
import com.angelvargas.data.executor.UiExecutor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesJobExecutor(): ThreadExecutor {
        return JobExecutor()
    }

    @Provides
    @Singleton
    fun providesPostExecutionThread(): PostExecutionThread {
        return UiExecutor()
    }

    @Provides
    @Singleton
    fun providesDatabaseProvider(): DatabaseProvider {
        return RealmProvider()
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(realmProvider: DatabaseProvider): LocalDataSource {
        return RealmResumeDataSource(realmProvider)
    }
}