package com.angelvargas.data.repository

import com.angelvargas.cvapp.domain.models.ResumeData
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.data.database.DatabaseProvider
import com.angelvargas.data.mappers.RealmResumeMapper
import com.angelvargas.data.mappers.ResumeDataMapper
import com.angelvargas.data.models.database.RealmResume
import com.angelvargas.data.remote.ResumeResponse
import com.angelvargas.data.services.ResumeApiServices
import io.reactivex.Single
import java.net.SocketTimeoutException

class ResumeDataRepository(
    private val resumeServices: ResumeApiServices,
    private val realmProvider: DatabaseProvider
): ResumeRepository {

    override fun getCvInformation(): Single<ResumeData> {
        return resumeServices.getResumeInformation()
                .map {
                    storeInRealm(it)
                    ResumeDataMapper().transform(it)
                }.onErrorResumeNext{ throwable -> Single.error(getResumeErrorHandler(throwable))}
    }

    private fun storeInRealm(resumeResponse: ResumeResponse) {
        realmProvider.instance.use { realm ->
            realm.executeTransaction {
                it.where(RealmResume::class.java).findAll().deleteAllFromRealm()
                val realmResume: RealmResume = RealmResumeMapper().transform(resumeResponse)
                it.copyToRealmOrUpdate(realmResume)
            }
        }
    }

    private fun getResumeErrorHandler(throwable: Throwable): Throwable {
        return when (throwable) {
            is SocketTimeoutException -> GetResumeInformationUseCase.ResumeException.TimeoutException()
            else -> GetResumeInformationUseCase.ResumeException.GenericError()
        }
    }
}