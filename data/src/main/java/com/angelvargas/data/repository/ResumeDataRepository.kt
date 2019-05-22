package com.angelvargas.data.repository

import com.angelvargas.cvapp.domain.models.ResumeData
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.data.database.LocalDataSource
import com.angelvargas.data.mappers.ResumeDataMapper
import com.angelvargas.data.remote.ResumeResponse
import com.angelvargas.data.services.ResumeApiServices
import io.reactivex.Single
import java.net.SocketTimeoutException

class ResumeDataRepository(
    private val resumeServices: ResumeApiServices,
    private val localDataSource: LocalDataSource
): ResumeRepository {

    override fun getCvInformation(): Single<ResumeData> {
        return resumeServices.getResumeInformation()
                .map {
                    storeInRealm(it)
                    ResumeDataMapper().transform(it)
                }.onErrorResumeNext{ getLocalCvInformation() }
    }

    override fun getLocalCvInformation(): Single<ResumeData> {
        return localDataSource.getLocalCvInformation()
            .onErrorResumeNext{ error -> Single.error(getResumeErrorHandler(error)) }
    }

    private fun storeInRealm(resumeResponse: ResumeResponse) {
        localDataSource.storeReceivedData(resumeResponse)
    }

    private fun getResumeErrorHandler(throwable: Throwable): Throwable {
        return when (throwable) {
            is SocketTimeoutException -> GetResumeInformationUseCase.ResumeException.TimeoutException()
            else -> GetResumeInformationUseCase.ResumeException.GenericError()
        }
    }
}