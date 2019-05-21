package com.angelvargas.cvapp.domain

import com.angelvargas.data.Work
import com.google.gson.annotations.SerializedName

data class Resume(
        @SerializedName("basics")
    val basics: Basics?,
        @SerializedName("work")
    val work: List<Work>?,
        @SerializedName("skills")
    val skills: List<Skills>?,
        @SerializedName("languages")
    val languages: List<Languages>?
)