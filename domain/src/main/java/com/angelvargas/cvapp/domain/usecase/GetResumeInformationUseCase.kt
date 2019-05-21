package com.angelvargas.cvapp.domain.usecase

import com.angelvargas.cvapp.domain.models.ResumeData
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import io.reactivex.Scheduler
import io.reactivex.Single

class GetResumeInformationUseCase(private val resumeRepository: ResumeRepository,
                                  private val executionScheduler: Scheduler,
                                  private val postExecutionScheduler: Scheduler) {

    fun execute(): Single<ResumeData> {
        return resumeRepository.getCvInformation()
                .subscribeOn(executionScheduler)
                .observeOn(postExecutionScheduler)
    }

    sealed class ResumeException {
        class ConnectionError: Exception()
        class TimeoutException: Exception()
        class GenericError: Exception()
    }
}