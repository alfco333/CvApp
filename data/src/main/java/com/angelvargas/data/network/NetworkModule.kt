package com.angelvargas.data.network

import com.angelvargas.data.BuildConfig
import com.angelvargas.data.services.ResumeApiServices
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(val baseUrl: String) {

    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson,
                         okHttpClient: OkHttpClient,
                         rx2AdapterFactory: RxJava2CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rx2AdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providesResumeApiService(retrofit: Retrofit): ResumeApiServices {
        return retrofit.create(ResumeApiServices::class.java)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(stethoInterceptor: StethoInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
        builder.retryOnConnectionFailure(false)
        builder.connectTimeout(TIME_OUT_API.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(TIME_OUT_API.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(TIME_OUT_API.toLong(), TimeUnit.SECONDS)
        builder.interceptors().add(loggingInterceptor.build())
        builder.networkInterceptors().add(stethoInterceptor)
        return  builder.build()
    }

    @Provides
    @Singleton
    fun providesStethoInterceptor(): StethoInterceptor {
        return StethoInterceptor()
    }

    @Provides
    @Singleton
    fun providesHttpLogginInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun providesRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    companion object {
        const val TIME_OUT_API = 30 // seconds
    }
}