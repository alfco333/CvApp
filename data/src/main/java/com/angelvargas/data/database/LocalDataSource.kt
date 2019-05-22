package com.angelvargas.data.database

import com.angelvargas.cvapp.domain.models.ResumeData
import com.angelvargas.data.remote.ResumeResponse
import io.reactivex.Single

interface LocalDataSource {

    fun storeReceivedData(resumeResponse: ResumeResponse)
    fun getLocalCvInformation(): Single<ResumeData>
}