package com.angelvargas.cvapp.domain.repository

import com.angelvargas.cvapp.domain.models.ResumeData
import io.reactivex.Single

interface ResumeRepository {

    fun getCvInformation(): Single<ResumeData>
    fun getLocalCvInformation(): Single<ResumeData>
}