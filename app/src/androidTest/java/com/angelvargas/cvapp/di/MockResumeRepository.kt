package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.domain.models.ResumeData
import com.angelvargas.cvapp.domain.repository.ResumeRepository
import io.reactivex.Single

class MockResumeRepository(private val resumeData: ResumeData): ResumeRepository {
    override fun getCvInformation(): Single<ResumeData> {
        return Single.just(resumeData)
    }

    override fun getLocalCvInformation(): Single<ResumeData> {
        return Single.just(resumeData)
    }
}