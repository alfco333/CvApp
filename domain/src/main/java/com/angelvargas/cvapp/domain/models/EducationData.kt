package com.angelvargas.cvapp.domain.models

data class EducationData(
    val institution: String?,
    val area: String?,
    val studyType: String?,
    val startDate: String?,
    val endDate: String?,
    val courses: List<String>?
)