package com.angelvargas.cvapp

import com.angelvargas.cvapp.domain.models.BasicsData
import com.angelvargas.cvapp.domain.models.SkillsData
import com.angelvargas.cvapp.domain.models.WorkData

interface ResumeContract {

    interface Presenter {

        fun initView()
        fun getResumeInformation()
        fun dispose()
    }

    interface View {

        fun initializeViewComponents()
        fun renderResumeInformation(basicsData: BasicsData, skillsData: List<SkillsData>, workData: List<WorkData>)
        fun renderBasicInformation(basicInformation: BasicsData)
        fun renderSkillsInformation(skillsInformation: List<SkillsData>)
        fun renderWorksInformation(workInformation: List<WorkData>)
        fun showResumePlaceHolder()
    }
}