package com.angelvargas.data.remote

import com.angelvargas.data.models.Basics
import com.angelvargas.data.models.Skills
import com.angelvargas.data.models.Work
import com.google.gson.annotations.SerializedName

data class ResumeResponse(
    @SerializedName("basics")
    val basics: Basics?,
    @SerializedName("work")
    val work: List<Work>?,
    @SerializedName("skills")
    val skills: List<Skills>?
)