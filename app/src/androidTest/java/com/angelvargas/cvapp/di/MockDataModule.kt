package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.domain.executor.PostExecutionThread
import com.angelvargas.cvapp.domain.executor.ThreadExecutor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class MockDataModule {

    @Provides
    @Singleton
    fun providesJobExecutor(): ThreadExecutor {
        return object: ThreadExecutor {
            override fun execute(runnable: Runnable?) {
                runnable?.run()
            }

        }
    }

    @Provides
    @Singleton
    fun providesPostExecutionThread(): PostExecutionThread {
        return object: PostExecutionThread {
            override fun getScheduler(): Scheduler {
                return Schedulers.trampoline()
            }
        }
    }
}