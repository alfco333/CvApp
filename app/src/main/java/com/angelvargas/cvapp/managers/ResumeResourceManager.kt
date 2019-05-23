package com.angelvargas.cvapp.managers

import android.content.res.Resources
import com.angelvargas.cvapp.R
import com.angelvargas.cvapp.domain.manager.ResourceManager

class ResumeResourceManager(private val resources: Resources): ResourceManager {

    override fun getConnectionErrorMessage(): String {
        return resources.getString(R.string.connection_error)
    }

    override fun getTimeoutExceptionMessage(): String {
        return resources.getString(R.string.timeout_error)
    }

    override fun getGenericExceptionMessage(): String {
        return resources.getString(R.string.generic_error)
    }

    override fun getSkillsHeader(): String {
        return ""
    }

    override fun getWorkPeriodFormat(startDate: String?, endDate: String?): String {
        return if (startDate.isNullOrEmpty() || endDate.isNullOrEmpty()) {
            ""
        } else {
            resources.getString(R.string.text_date_template_format, startDate, endDate)
        }
    }

    override fun getSkillsSectionTitle(): String {
        return resources.getString(R.string.title_skills_list)
    }

    override fun getWorkSectionTitle(): String {
        return resources.getString(R.string.title_previous_experience)
    }
}