package com.angelvargas.data.network

import com.angelvargas.cvapp.domain.remote.ServiceFactory
import com.angelvargas.data.BuildConfig
import com.angelvargas.data.services.ResumeApiServices.Companion.BASE_URL
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiServiceFactory: ServiceFactory {

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        builder.retryOnConnectionFailure(false)
        builder.connectTimeout(TIME_OUT_API.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(TIME_OUT_API.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(TIME_OUT_API.toLong(), TimeUnit.SECONDS)

        val loggingInterceptor = LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .addHeader("version", BuildConfig.VERSION_NAME)

        builder.interceptors().add(loggingInterceptor.build())
        builder.networkInterceptors().add(StethoInterceptor())
        return builder.build()
    }
    override fun <T> makeApiService(serviceClass: Class<T>): T {
        val okHttpClient = createOkHttpClient()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(serviceClass)
    }

    companion object {
        const val TIME_OUT_API = 30 // seconds
    }
}