package com.angelvargas.cvapp.domain.usecase

import com.angelvargas.cvapp.domain.executor.PostExecutionThread
import com.angelvargas.cvapp.domain.executor.ThreadExecutor
import com.angelvargas.cvapp.domain.models.ResumeData
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetResumeInformationUseCase @Inject constructor(val resumeRepository: ResumeRepository,
                                                      val executionScheduler: ThreadExecutor,
                                                      val postExecutionScheduler: PostExecutionThread) {

    fun execute(): Single<ResumeData> {
        return resumeRepository.getCvInformation()
                .subscribeOn(Schedulers.from(executionScheduler))
                .observeOn(postExecutionScheduler.getScheduler())
    }

    sealed class ResumeException {
        class ConnectionError: Exception()
        class TimeoutException: Exception()
        class GenericError: Exception()
    }
}