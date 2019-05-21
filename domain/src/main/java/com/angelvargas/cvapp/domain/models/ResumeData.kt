package com.angelvargas.cvapp.domain.models

data class ResumeData(
        val basics: BasicsData?,
        val work: List<WorkData>?,
        val skills: List<SkillsData>?,
        val languages: List<LanguagesData>?
)