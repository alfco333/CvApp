package com.angelvargas.data.database

import android.util.Log
import com.angelvargas.cvapp.domain.models.ResumeData
import com.angelvargas.cvapp.domain.usecase.GetResumeInformationUseCase
import com.angelvargas.data.mappers.RealmResumeMapper
import com.angelvargas.data.mappers.ResumeRealmDataMapper
import com.angelvargas.data.models.database.RealmResume
import com.angelvargas.data.remote.ResumeResponse
import io.reactivex.Single

class RealmResumeDataSource(private val realmProvider: DatabaseProvider): LocalDataSource {

    override fun storeReceivedData(resumeResponse: ResumeResponse) {
        realmProvider.instance.use { realm ->
            realm.executeTransaction {
                it.deleteAll()
                val realmResume: RealmResume = RealmResumeMapper().transform(resumeResponse)
                it.copyToRealmOrUpdate(realmResume)
                Log.d("RealmDataSource", "Data Stored successfully")
            }
        }
    }

    override fun getLocalCvInformation(): Single<ResumeData> {
        return Single.fromCallable {
            return@fromCallable realmProvider.instance.use {
                val realmResults = it.where(RealmResume::class.java).findFirst()
                if (realmResults == null) {
                    Log.d("RealmDataSource", "Result: no Results")
                    throw GetResumeInformationUseCase.ResumeException.GenericError()
                } else {
                    Log.d("RealmDataSource", "Result: " + realmResults.basics?.name)
                    ResumeRealmDataMapper().transform(realmResults)
                }
            }
        }
    }
}