package com.angelvargas.cvapp.domain.models

data class ResumeData(
        val basics: BasicsData? = null,
        val work: List<WorkData>? = null,
        val skills: List<SkillsData>? = null
)