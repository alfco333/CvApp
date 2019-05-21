package com.angelvargas.cvapp.domain.manager

interface ResourceManager {

    fun getConnectionErrorMessage(): String
    fun getTimeoutExceptionMessage(): String
    fun getGenericExceptionMessage(): String
    fun getSkillsHeader(): String
}