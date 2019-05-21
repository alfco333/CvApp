package com.angelvargas.data.repository

import com.angelvargas.cvapp.domain.models.ResumeData
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.data.mappers.ResumeDataMapper
import com.angelvargas.data.services.ResumeApiServices
import io.reactivex.Single
import java.net.SocketTimeoutException

class ResumeDataRepository(private val resumeServices: ResumeApiServices): ResumeRepository {

    override fun getCvInformation(): Single<ResumeData> {
        return resumeServices.getResumeInformation()
                .map { ResumeDataMapper().transform(it) }
                .onErrorResumeNext{ throwable -> Single.error(getResumeErrorHandler(throwable))}
    }

    private fun getResumeErrorHandler(throwable: Throwable): Throwable {
        return when (throwable) {
            is SocketTimeoutException -> GetResumeInformationUseCase.ResumeException.TimeoutException()
            else -> GetResumeInformationUseCase.ResumeException.GenericError()
        }
    }
}