package com.angelvargas.data.services

import com.angelvargas.cvapp.domain.ResumeResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ResumeApiServices {

    @GET(ENDPOINT_CV)
    fun getResumeInformation(): Single<ResumeResponse>

    companion object {
        const val ENDPOINT_CV = "cv"
        const val BASE_URL =  "http://demo6959920.mockable.io/"
    }
}