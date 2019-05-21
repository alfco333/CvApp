package com.angelvargas.data.models

import com.google.gson.annotations.SerializedName

data class Education(
    @SerializedName("institution")
    val institution: String?,
    @SerializedName("area")
    val area: String?,
    @SerializedName("studyType")
    val studyType: String?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("courses")
    val courses: List<String>?
)