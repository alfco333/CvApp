package com.angelvargas.cvapp.domain.models

data class WorkData(
        val company: String?,
        val position: String?,
        val urlImage: String?,
        val startDate: String?,
        val endDate: String?,
        val summary: String?,
        val highlights: List<String>?
)