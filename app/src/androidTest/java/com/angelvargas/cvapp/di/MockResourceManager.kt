package com.angelvargas.cvapp.di

import com.angelvargas.cvapp.domain.manager.ResourceManager
import com.angelvargas.cvapp.presenter.MainActivityTest.Companion.SKILLS_HEADER_TITLE
import com.angelvargas.cvapp.presenter.MainActivityTest.Companion.WORKS_HEADER_TITLE

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
        return ""
    }

    override fun getWorkPeriodFormat(startDate: String?, endDate: String?): String {
        return ""
    }

    override fun getSkillsSectionTitle(): String {
        return SKILLS_HEADER_TITLE
    }

    override fun getWorkSectionTitle(): String {
        return WORKS_HEADER_TITLE
    }
}