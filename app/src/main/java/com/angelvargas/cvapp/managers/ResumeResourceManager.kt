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
}