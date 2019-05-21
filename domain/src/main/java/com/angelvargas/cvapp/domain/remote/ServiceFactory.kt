package com.angelvargas.cvapp.domain.remote

interface ServiceFactory {
    fun <T> makeApiService(serviceClass: Class<T>): T
}