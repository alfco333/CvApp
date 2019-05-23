package com.angelvargas.cvapp.domain.manager

interface ResourceManager {

    fun getConnectionErrorMessage(): String
    fun getTimeoutExceptionMessage(): String
    fun getGenericExceptionMessage(): String
    fun getSkillsHeader(): String
    fun getWorkPeriodFormat(startDate: String?, endDate: String?): String
    fun getSkillsSectionTitle(): String
    fun getWorkSectionTitle(): String
}