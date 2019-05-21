package com.angelvargas.cvapp.domain

import com.angelvargas.data.models.Basics
import com.angelvargas.data.models.Languages
import com.angelvargas.data.models.Skills
import com.angelvargas.data.models.Work
import com.google.gson.annotations.SerializedName

data class ResumeResponse(
        @SerializedName("basics")
    val basics: Basics?,
        @SerializedName("work")
    val work: List<Work>?,
        @SerializedName("skills")
    val skills: List<Skills>?,
        @SerializedName("languages")
    val languages: List<Languages>?
)