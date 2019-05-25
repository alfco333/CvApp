package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.domain.manager.ResourceManager

class MockResourceManager: ResourceManager {
    override fun getConnectionErrorMessage(): String {
        return ""
    }

    override fun getTimeoutExceptionMessage(): String {
        return ""
    }

    override fun getGenericExceptionMessage(): String {
        return ""
    }

    override fun getSkillsHeader(): String {
        return "Skills"
    }

    override fun getWorkPeriodFormat(startDate: String?, endDate: String?): String {
        return ""
    }

    override fun getSkillsSectionTitle(): String {
        return "Skills"
    }

    override fun getWorkSectionTitle(): String {
        return ""
    }
}